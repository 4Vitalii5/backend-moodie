package org.cyberrealm.tech.muvio.service;

import java.util.Set;
import org.cyberrealm.tech.muvio.model.Actor;
import org.cyberrealm.tech.muvio.model.Director;
import org.cyberrealm.tech.muvio.model.Photo;
import org.cyberrealm.tech.muvio.model.Review;

public interface TmdbService {
    void loadGenresIntoRepository();

    void loadPopularMoviesIntoRepository();

    Set<Actor> getActors(int movieId);

    Set<Photo> getPhotos(int movieId);

    Set<Review> getReviews(int movieId);

    Director getDirector(int movieId);
}
