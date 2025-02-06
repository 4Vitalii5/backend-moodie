package org.cyberrealm.tech.muvio.dto.tmdb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TmdbMovie(
        boolean adult,
        String backdrop_path,
        List<Integer> genre_ids,
        Integer id,
        String original_language,
        String original_title,
        String overview,
        BigDecimal popularity,
        String poster_path,
        LocalDate release_date,
        String title,
        Boolean video,
        BigDecimal vote_average,
        Integer vote_count
) {
}
