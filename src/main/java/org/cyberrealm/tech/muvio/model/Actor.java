package org.cyberrealm.tech.muvio.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("actors")
public class Actor {
    @MongoId
    private String id;
    private String name;
    private String profilePath;
    private String character;
}
