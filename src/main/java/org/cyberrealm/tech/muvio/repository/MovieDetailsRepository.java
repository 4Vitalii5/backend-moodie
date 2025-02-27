package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.MovieDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieDetailsRepository extends MongoRepository<MovieDetails, String> {
}
