package org.cyberrealm.tech.muvio.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("reviews")
public class Review {
    @Id
    private String id;
    private String author;
    private String avatarPath;
    private String rating;
    private String content;
    //private Integer mediaId;
}
