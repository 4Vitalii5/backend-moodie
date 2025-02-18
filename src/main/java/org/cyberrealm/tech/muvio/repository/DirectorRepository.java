package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.Director;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectorRepository extends MongoRepository<Director, String> {
}
