package org.cyberrealm.tech.muvio.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.dto.MovieDto;
import org.cyberrealm.tech.muvio.repository.movies.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor()
@RequestMapping("movies")
public class MovieController {
    private final MovieRepository movieRepository;

    @GetMapping
    public List<MovieDto> getAll(Pageable pageable) {
        return List.of();
    }
}
