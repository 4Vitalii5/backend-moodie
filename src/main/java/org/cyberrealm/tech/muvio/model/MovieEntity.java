package org.cyberrealm.tech.muvio.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
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
    private Set<MovieGenre> genres;
    private String posterPath;
    private Double voteAverage;
    private Integer duration;
    private String overview;
    private String trailerPath;
    private BigDecimal popularity;
    private Integer voteCount;
    private List<Actor> actors;
    private List<Photo> photos;
    private List<Provider> providers;
    private List<Review> reviews;
    private Director director;
}
