package org.cyberrealm.tech.muvio.service.impl;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.core.image.Artwork;
import info.movito.themoviedbapi.model.core.video.Video;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Crew;
import info.movito.themoviedbapi.tools.TmdbException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.exception.TmdbProcessingException;
import org.cyberrealm.tech.muvio.mapper.TmdbActorMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbDirectorMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbGenreMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbMovieMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbPhotoMapper;
import org.cyberrealm.tech.muvio.mapper.TmdbReviewMapper;
import org.cyberrealm.tech.muvio.model.Actor;
import org.cyberrealm.tech.muvio.model.Director;
import org.cyberrealm.tech.muvio.model.GenreEntity;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.cyberrealm.tech.muvio.model.Photo;
import org.cyberrealm.tech.muvio.model.Review;
import org.cyberrealm.tech.muvio.repository.ActorRepository;
import org.cyberrealm.tech.muvio.repository.DirectorRepository;
import org.cyberrealm.tech.muvio.repository.GenreRepository;
import org.cyberrealm.tech.muvio.repository.MovieRepository;
import org.cyberrealm.tech.muvio.repository.PhotoRepository;
import org.cyberrealm.tech.muvio.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmdbService {
    public static final String EN = "en";
    public static final String UKRAINE = "UA";
    public static final int PAGE = 1;
    public static final int MAX_NUMBER_OF_PAGES = 5;
    public static final String TRAILER = "Trailer";
    public static final String YOU_TUBE = "YouTube";
    public static final int MAX_SIZE_OF_RECORDS = 10;
    public static final String DIRECTOR = "Director";
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final PhotoRepository photoRepository;
    private final ReviewRepository reviewRepository;
    private final DirectorRepository directorRepository;
    private final TmdbGenreMapper tmdbGenreMapper;
    private final TmdbMovieMapper tmdbMovieMapper;
    private final TmdbActorMapper tmdbActorMapper;
    private final TmdbPhotoMapper tmdbPhotoMapper;
    private final TmdbReviewMapper tmdbReviewMapper;
    private final TmdbDirectorMapper tmdbDirectorMapper;
    private final TmdbApi tmdbApi;

    @PostConstruct
    public void init() {
        loadGenresIntoRepository();
        loadPopularMoviesIntoRepository();
    }

    public void loadGenresIntoRepository() {
        List<Genre> tmdbGenres;
        try {
            tmdbGenres = tmdbApi.getGenre().getMovieList(EN);
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load genres from TMDB: " + e.getMessage());
        }
        for (Genre tmdbGenre : tmdbGenres) {
            if (!genreRepository.existsById(String.valueOf(tmdbGenre.getId()))) {
                GenreEntity genreModel = tmdbGenreMapper.toGenre(tmdbGenre);
                genreRepository.save(genreModel);
            }
        }
    }

    public void loadPopularMoviesIntoRepository() {
        List<Movie> allMovies = new ArrayList<>();
        try {
            for (int page = PAGE; page <= MAX_NUMBER_OF_PAGES; page++) {
                MovieResultsPage moviesPage = tmdbApi.getMovieLists()
                        .getPopular(EN, page, UKRAINE);
                allMovies.addAll(moviesPage.getResults());
            }
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load popular movies from TMDB: "
                    + e.getMessage());
        }
        for (Movie movie : allMovies) {
            if (!movieRepository.existsById(String.valueOf(movie.getId()))) {
                List<String> genreIds = movie.getGenreIds().stream()
                        .map(String::valueOf)
                        .toList();
                Set<GenreEntity> genres = genreRepository.findGenresById(genreIds);
                MovieEntity movieModel = tmdbMovieMapper.toMovie(movie);
                movieModel.setGenres(genres);
                movieModel.setActors(getActors(movie.getId()));
                movieModel.setPhotos(getPhotos(movie.getId()));
                movieModel.setReviews(getReviews(movie.getId()));
                movieModel.setDirector(getDirector(movie.getId()));
                movieModel.setTrailerPath(getTrailer(movie.getId()));
                movieRepository.save(movieModel);
            }
        }
    }

    private String getTrailer(int movieId) {
        List<Video> videos;
        try {
            videos = tmdbApi.getMovies().getVideos(movieId, EN).getResults();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load trailer from TMDB: "
                    + e.getMessage());
        }
        return videos.stream()
                .filter(video -> TRAILER.equalsIgnoreCase(video.getType())
                        && YOU_TUBE.equalsIgnoreCase(video.getSite()))
                .findFirst()
                .map(Video::getKey)
                .orElse(null);
    }

    public Set<Actor> getActors(int movieId) {
        List<Cast> castList;
        try {
            castList = tmdbApi.getMovies().getCredits(movieId, EN).getCast();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load cast from TMDB: " + e.getMessage());
        }
        return castList.stream()
                .limit(MAX_SIZE_OF_RECORDS)
                .map(tmdbActorMapper::toActor)
                .map(actorRepository::save)
                .collect(Collectors.toSet());
    }

    public Set<Photo> getPhotos(int movieId) {
        List<Artwork> posters;
        try {
            posters = tmdbApi.getMovies().getImages(movieId, EN).getPosters();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load photos from TMDB: " + e.getMessage());
        }
        return posters.stream()
                .limit(MAX_SIZE_OF_RECORDS)
                .map(tmdbPhotoMapper::toPhoto)
                .map(photoRepository::save)
                .collect(Collectors.toSet());
    }

    public Set<Review> getReviews(int movieId) {
        List<info.movito.themoviedbapi.model.core.Review> reviews;
        try {
            reviews = tmdbApi.getMovies().getReviews(movieId, EN, PAGE).getResults();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load reviews from TMDB: " + e.getMessage());
        }
        return reviews.stream()
                .map(tmdbReviewMapper::toReview)
                .map(reviewRepository::save)
                .collect(Collectors.toSet());
    }

    public Director getDirector(int movieId) {
        List<Crew> crewList;
        try {
            crewList = tmdbApi.getMovies().getCredits(movieId, EN).getCrew();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load crew from TMDB: " + e.getMessage());
        }
        return crewList.stream()
                .filter(crew -> DIRECTOR.equalsIgnoreCase(crew.getJob()))
                .map(tmdbDirectorMapper::toProducer)
                .map(directorRepository::save)
                .findFirst()
                .orElse(null);
    }
}
