package org.cyberrealm.tech.muvio.dto.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TmdbResponse(
        int page,
        @JsonProperty("results") List<TmdbMovie> movies
) {
}
