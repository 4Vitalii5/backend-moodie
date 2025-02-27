package org.cyberrealm.tech.muvio.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("movies")
public class MovieEntity {
    @MongoId
    private String id;
    private String title;
    private String releaseDate;
    @DBRef
    private Set<GenreEntity> genres = new HashSet<>();
    private String posterPath;
    private Double voteAverage;
    private Integer duration;
    @DBRef
    private MovieDetails movieDetails;
}
