package org.cyberrealm.tech.muvio.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("providers")
public class Provider {
    @MongoId
    private ObjectId id;
    private Integer displayPriority;
    private String logoPath;
    private String providerName;
}
