package org.cyberrealm.tech.muvio.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.TmdbReviewMapper;
import org.cyberrealm.tech.muvio.model.Review;
import org.cyberrealm.tech.muvio.repository.ReviewRepository;
import org.cyberrealm.tech.muvio.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final TmdbReviewMapper tmdbReviewMapper;
    private final TmdbClientService tmdbClientService;

    @Override
    public Set<Review> getReviews(int movieId) {
        return tmdbClientService.fetchMovieReviews(movieId).stream()
                .map(tmdbReviewMapper::toReview)
                .map(reviewRepository::save)
                .collect(Collectors.toSet());
    }
}
