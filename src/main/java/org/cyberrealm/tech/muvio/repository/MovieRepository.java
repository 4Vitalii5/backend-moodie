package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<MovieEntity, String> {
}
