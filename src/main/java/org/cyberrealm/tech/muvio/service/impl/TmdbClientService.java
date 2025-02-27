package org.cyberrealm.tech.muvio.service.impl;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.image.Artwork;
import info.movito.themoviedbapi.model.core.video.Video;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Crew;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.exception.TmdbProcessingException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmdbClientService {
    private static final String EN = "en";
    private static final String UKRAINE = "UA";
    private static final int PAGE = 1;
    private static final int MAX_NUMBER_OF_PAGES = 5;
    private static final String TRAILER = "Trailer";
    private static final String YOU_TUBE = "YouTube";
    private static final String DIRECTOR = "Director";

    private final TmdbApi tmdbApi;

    public List<Genre> fetchGenres() {
        try {
            return tmdbApi.getGenre().getMovieList(EN);
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load genres from TMDB: " + e.getMessage());
        }
    }

    public List<Movie> fetchPopularMovies() {
        List<Movie> allMovies = new ArrayList<>();
        try {
            for (int page = PAGE; page <= MAX_NUMBER_OF_PAGES; page++) {
                allMovies.addAll(tmdbApi.getMovieLists().getPopular(EN, page, UKRAINE)
                        .getResults());
            }
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load popular movies from TMDB: "
                    + e.getMessage());
        }
        return allMovies;
    }

    public List<Cast> fetchMovieCast(int movieId) {
        try {
            return tmdbApi.getMovies().getCredits(movieId, EN).getCast();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load cast from TMDB: " + e.getMessage());
        }
    }

    public List<Artwork> fetchMoviePhotos(int movieId) {
        try {
            return tmdbApi.getMovies().getImages(movieId, EN).getPosters();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load photos from TMDB: " + e.getMessage());
        }
    }

    public List<info.movito.themoviedbapi.model.core.Review> fetchMovieReviews(int movieId) {
        try {
            return tmdbApi.getMovies().getReviews(movieId, EN, PAGE).getResults();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load reviews from TMDB: " + e.getMessage());
        }
    }

    public Optional<Crew> fetchDirector(int movieId) {
        try {
            return tmdbApi.getMovies().getCredits(movieId, EN).getCrew().stream()
                    .filter(crew -> DIRECTOR.equalsIgnoreCase(crew.getJob()))
                    .findFirst();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load director from TMDB: " + e.getMessage());
        }
    }

    public String fetchTrailer(int movieId) {
        try {
            return tmdbApi.getMovies().getVideos(movieId, EN).getResults().stream()
                    .filter(video -> TRAILER.equalsIgnoreCase(video.getType())
                            && YOU_TUBE.equalsIgnoreCase(video.getSite()))
                    .map(Video::getKey)
                    .findFirst()
                    .orElse(null);
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load trailer from TMDB: " + e.getMessage());
        }
    }

    public Integer fetchMovieDuration(int movieId) {
        try {
            return tmdbApi.getMovies().getDetails(movieId, EN).getRuntime();
        } catch (TmdbException e) {
            throw new TmdbProcessingException("Can't load movie duration from TMDB: "
                    + e.getMessage());
        }
    }

    //    public List<Provider> fetchProviders(int movieId) {
    //        try {
    //            return tmdbApi.getMovies().getWatchProviders(movieId).getResults().get("UA")
    //                    .getRentProviders();
    //        } catch (TmdbException e) {
    //            throw new TmdbProcessingException("Can't load movie providers from TMDB: "
    //                    + e.getMessage());
    //        }
    //    }
}
