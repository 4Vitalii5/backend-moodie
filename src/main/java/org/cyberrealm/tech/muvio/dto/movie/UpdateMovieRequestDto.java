package org.cyberrealm.tech.muvio.dto.movie;

import java.time.LocalDate;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.GenreEntity;

public record UpdateMovieRequestDto(
        String title,
        Set<GenreEntity> genres,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate,
        String videoUrl
) {
}
