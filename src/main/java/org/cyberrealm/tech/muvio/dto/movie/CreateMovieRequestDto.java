package org.cyberrealm.tech.muvio.dto.movie;

import java.time.LocalDate;
import java.util.Set;

public record CreateMovieRequestDto(
        String title,
        Set<Integer> genreIds,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate,
        String videoUrl
) {
}
