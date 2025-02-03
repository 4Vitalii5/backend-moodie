package org.cyberrealm.tech.muvio.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("movie")
public class Movie {
    @Id
    private String id;


}
