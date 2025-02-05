package org.cyberrealm.tech.muvio.dto;

import java.time.LocalDate;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.Genre;

public record UpdateMovieRequestDto(
        String title,
        Set<Genre> genres,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate,
        String videoUrl
) {
}
