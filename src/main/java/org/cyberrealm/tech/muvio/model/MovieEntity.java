package org.cyberrealm.tech.muvio.model;

import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("movies")
public class MovieEntity {
    @Id
    private String id;
    private String title;
    @DBRef
    private Set<GenreEntity> genres = new HashSet<>();
    @DBRef
    private Set<Actor> actors = new HashSet<>();
    @DBRef
    private Set<Photo> photos = new HashSet<>();
    @DBRef
    private Set<Review> reviews = new HashSet<>();
    @DBRef
    private Director director;
    private String trailerPath;
    private String overview;
    private Double voteAverage;
    private String posterPath;
    private String releaseDate;
    private BigDecimal popularity;
    private Integer voteCount;
}
