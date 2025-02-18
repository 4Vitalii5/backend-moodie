package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
