package org.cyberrealm.tech.muvio.model;

import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("movie")
@CompoundIndex(name = "genre_name_idx", def = "{'genres.name': 1}")
public class Movie {
    @Id
    private String id;
    private String title;
    private Set<Genre> genres = new HashSet<>();
    private String overview;
    private Double voteAverage;
    private String posterPath;
    private LocalDate releaseDate;
    private String videoUrl;
}
