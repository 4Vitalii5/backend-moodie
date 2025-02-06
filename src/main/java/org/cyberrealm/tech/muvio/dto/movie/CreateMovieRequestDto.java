package org.cyberrealm.tech.muvio.dto.movie;

import java.time.LocalDate;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.Genre;

public record CreateMovieRequestDto(
        String title,
        Set<Genre> genres,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate,
        String videoUrl
) {
}
