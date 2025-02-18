package org.cyberrealm.tech.muvio.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("producers")
public class Director {
    @Id
    private String id;
    private String originalName;
}
