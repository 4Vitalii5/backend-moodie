package org.cyberrealm.tech.muvio.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("reviews")
public class Review {
    @MongoId
    private String id;
    private String author;
    private String avatarPath;
    private String rating;
    private String content;
}
