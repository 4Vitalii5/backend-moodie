package org.cyberrealm.tech.muvio.repository;

import org.cyberrealm.tech.muvio.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
