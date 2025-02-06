package org.cyberrealm.tech.muvio.service.impl;

import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.dto.tmdb.TmdbResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TmdbMovieService {
    private final RestTemplate restTemplate;
    @Value("${tmdb.api.url}")
    private String apiUrl;
    @Value("${tmdb.api.key}")
    private String apiKey;

    public TmdbResponse getPopularMovies() {
        String url = String.format("%s/movie/popular?language=en-US&page=1", apiUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<TmdbResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, TmdbResponse.class);

        return response.getBody();
    }
}
