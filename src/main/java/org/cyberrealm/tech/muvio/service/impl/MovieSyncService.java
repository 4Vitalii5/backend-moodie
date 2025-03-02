package org.cyberrealm.tech.muvio.service.impl;

import info.movito.themoviedbapi.model.core.Movie;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.ActorMapper;
import org.cyberrealm.tech.muvio.mapper.DirectorMapper;
import org.cyberrealm.tech.muvio.mapper.MovieMapper;
import org.cyberrealm.tech.muvio.mapper.PhotoMapper;
import org.cyberrealm.tech.muvio.mapper.ReviewMapper;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.cyberrealm.tech.muvio.model.MovieGenre;
import org.cyberrealm.tech.muvio.repository.MovieRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieSyncService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final ActorMapper actorMapper;
    private final DirectorMapper directorMapper;
    private final PhotoMapper photoMapper;
    private final ReviewMapper reviewMapper;

    private final TmdbClientService tmdbClientService;

    @PostConstruct
    public void loadPopularMoviesIntoRepository() {
        updatePopularMovies();
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    public void updatePopularMovies() {
        List<MovieEntity> movies = tmdbClientService.fetchPopularMovies().stream()
                .filter(movie -> !movieRepository.existsById(String.valueOf(movie.getId())))
                .map(this::createMovieEntity)
                .toList();
        movieRepository.saveAll(movies);
    }

    private MovieEntity createMovieEntity(Movie movie) {
        int movieId = movie.getId();
        Set<MovieGenre> genres = movie.getGenreIds().stream().map(MovieGenre::fromId)
                .collect(Collectors.toSet());
        MovieEntity movieEntity = movieMapper.toMovie(movie);
        movieEntity.setGenres(genres);
        movieEntity.setDuration(tmdbClientService.fetchMovieDuration(movieId));
        movieEntity.setActors(tmdbClientService.fetchMovieCast(movieId).stream()
                .map(actorMapper::toActor)
                .toList());
        movieEntity.setPhotos(tmdbClientService.fetchMoviePhotos(movieId).stream()
                .map(photoMapper::toPhoto)
                .toList());
        movieEntity.setReviews(tmdbClientService.fetchMovieReviews(movieId).stream()
                .map(reviewMapper::toReview)
                .toList());
        movieEntity.setDirector(tmdbClientService.fetchDirector(movieId)
                .map(directorMapper::toProducer)
                .orElse(null));
        movieEntity.setTrailerPath(tmdbClientService.fetchTrailer(movieId));
        return movieEntity;
    }
}
