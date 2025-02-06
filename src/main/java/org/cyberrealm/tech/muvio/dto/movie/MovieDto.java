package org.cyberrealm.tech.muvio.dto.movie;

import java.time.LocalDate;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.Genre;

public record MovieDto(
        String id,
        String title,
        Set<Genre> genres,
        String overview,
        Double voteAverage,
        String posterPath,
        LocalDate releaseDate,
        String videoUrl
) {
}
