package org.cyberrealm.tech.muvio.service.impl;

import info.movito.themoviedbapi.model.core.Movie;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.dto.movie.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieSearchParameters;
import org.cyberrealm.tech.muvio.dto.movie.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.exception.EntityNotFoundException;
import org.cyberrealm.tech.muvio.mapper.MovieDetailsMapper;
import org.cyberrealm.tech.muvio.mapper.MovieMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbMovieMapper;
import org.cyberrealm.tech.muvio.model.GenreEntity;
import org.cyberrealm.tech.muvio.model.MovieDetails;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.cyberrealm.tech.muvio.repository.GenreRepository;
import org.cyberrealm.tech.muvio.repository.MovieDetailsRepository;
import org.cyberrealm.tech.muvio.repository.MovieRepository;
import org.cyberrealm.tech.muvio.service.ActorService;
import org.cyberrealm.tech.muvio.service.DirectorService;
import org.cyberrealm.tech.muvio.service.GenreService;
import org.cyberrealm.tech.muvio.service.MovieService;
import org.cyberrealm.tech.muvio.service.PhotoService;
import org.cyberrealm.tech.muvio.service.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieDetailsRepository movieDetailsRepository;
    private final ActorService actorService;
    private final PhotoService photoService;
    private final ReviewService reviewService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final MovieMapper movieMapper;
    private final MovieDetailsMapper movieDetailsMapper;
    private final TmdbMovieMapper tmdbMovieMapper;
    private final TmdbClientService tmdbClientService;

    @PostConstruct
    public void loadPopularMoviesIntoRepository() {
        genreService.loadGenresIntoRepository();
        List<Movie> movies = tmdbClientService.fetchPopularMovies();
        movies.stream()
                .filter(movie -> !movieRepository.existsById(String.valueOf(movie.getId())))
                .forEach(this::saveMovie);
    }

    @Override
    public List<MovieDto> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public MovieDto save(CreateMovieRequestDto requestDto) {
        MovieEntity movie = movieMapper.toMovie(requestDto);
        movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public MovieDto findById(String id) {
        return movieMapper.toDto(getMovieById(id));
    }

    @Override
    public MovieDto updateMovieById(String id, UpdateMovieRequestDto requestDto) {
        MovieEntity movie = getMovieById(id);
        movieMapper.updateMovieFromDto(requestDto, movie);
        movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieDto> search(MovieSearchParameters searchParameters, Pageable pageable) {
        return List.of();
    }

    private MovieEntity getMovieById(String id) {
        return movieRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find movie by id:" + id)
        );
    }

    private void saveMovie(Movie movie) {
        MovieEntity movieEntity = createMovieEntity(movie);
        MovieDetails movieDetails = createMovieDetails(movie);
        movieDetailsRepository.save(movieDetails);
        movieEntity.setMovieDetails(movieDetails);
        movieRepository.save(movieEntity);
    }

    private MovieEntity createMovieEntity(Movie movie) {
        List<String> genreIds = movie.getGenreIds().stream().map(String::valueOf).toList();
        Set<GenreEntity> genres = genreRepository.findGenresById(genreIds);
        MovieEntity movieEntity = tmdbMovieMapper.toMovie(movie);
        movieEntity.setGenres(genres);
        movieEntity.setDuration(tmdbClientService.fetchMovieDuration(movie.getId()));
        return movieEntity;
    }

    private MovieDetails createMovieDetails(Movie movie) {
        MovieDetails movieDetails = movieDetailsMapper.toMovieDetails(movie);
        movieDetails.setActors(actorService.getActors(movie.getId()));
        movieDetails.setPhotos(photoService.getPhotos(movie.getId()));
        movieDetails.setReviews(reviewService.getReviews(movie.getId()));
        movieDetails.setDirector(directorService.getDirector(movie.getId()));
        movieDetails.setTrailerPath(tmdbClientService.fetchTrailer(movie.getId()));
        return movieDetails;
    }
}
