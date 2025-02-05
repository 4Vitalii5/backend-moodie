package org.cyberrealm.tech.muvio.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.dto.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.MovieDto;
import org.cyberrealm.tech.muvio.dto.MovieSearchParameters;
import org.cyberrealm.tech.muvio.dto.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.service.MovieService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor()
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAll(Pageable pageable) {
        return movieService.findAll(pageable);
    }

    @PostMapping
    public MovieDto createMovie(CreateMovieRequestDto requestDto) {
        return movieService.save(requestDto);
    }

    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable String id) {
        return movieService.findById(id);
    }

    @PutMapping("{id}")
    public MovieDto updateMovie(@PathVariable String id,
                                @RequestBody @Valid UpdateMovieRequestDto requestDto) {
        return movieService.updateMovieById(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable String id) {
        movieService.deleteById(id);
    }

    @GetMapping("/search")
    public List<MovieDto> searchMovies(@Valid MovieSearchParameters searchParameters,
                                       Pageable pageable) {
        return movieService.search(searchParameters, pageable);
    }
}
