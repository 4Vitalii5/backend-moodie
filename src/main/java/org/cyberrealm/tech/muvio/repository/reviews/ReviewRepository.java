package org.cyberrealm.tech.muvio.repository.reviews;

import org.cyberrealm.tech.muvio.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
}
