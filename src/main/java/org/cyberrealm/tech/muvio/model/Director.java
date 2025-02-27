package org.cyberrealm.tech.muvio.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("producers")
public class Director {
    @MongoId
    private String id;
    private String originalName;
}
