package org.cyberrealm.tech.muvio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("movie_details")
public class MovieDetails {
    @MongoId
    private String id;
    private String overview;
    private String trailerPath;
    @DBRef
    private Set<Actor> actors = new HashSet<>();
    @DBRef
    private Set<Photo> photos = new HashSet<>();
    @DBRef
    private List<Provider> providers = new ArrayList<>();
    @DBRef
    private Set<Review> reviews = new HashSet<>();
    @DBRef
    private Director director;
    private BigDecimal popularity;
    private Integer voteCount;
}
