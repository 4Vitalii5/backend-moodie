package org.cyberrealm.tech.muvio.dto.movie;

import java.time.LocalDate;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.Actor;
import org.cyberrealm.tech.muvio.model.Director;
import org.cyberrealm.tech.muvio.model.MovieGenre;
import org.cyberrealm.tech.muvio.model.Photo;
import org.cyberrealm.tech.muvio.model.Review;

public record MovieDto(
        String id,
        String title,
        Set<MovieGenre> genres,
        Set<Actor> actors,
        Set<Photo> photos,
        Set<Review> reviews,
        Director director,
        String trailerPath,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate
) {
}
