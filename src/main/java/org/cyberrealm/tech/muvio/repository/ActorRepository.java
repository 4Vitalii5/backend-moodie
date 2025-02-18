package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorRepository extends MongoRepository<Actor, String> {
}
