package org.cyberrealm.tech.muvio.service;

import java.util.Set;
import org.cyberrealm.tech.muvio.model.Review;

public interface ReviewService {
    Set<Review> getReviews(int movieId);
}
