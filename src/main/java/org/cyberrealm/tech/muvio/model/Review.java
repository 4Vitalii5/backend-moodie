package org.cyberrealm.tech.muvio.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private String id;
    private String author;
    private String avatarPath;
    private String rating;
    private String content;
}
