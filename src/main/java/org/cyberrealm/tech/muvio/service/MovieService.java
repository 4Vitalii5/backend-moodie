package org.cyberrealm.tech.muvio.service;

import java.util.List;
import org.cyberrealm.tech.muvio.dto.movie.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieSearchParameters;
import org.cyberrealm.tech.muvio.dto.movie.UpdateMovieRequestDto;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    List<MovieDto> findAll(Pageable pageable);

    MovieDto save(CreateMovieRequestDto requestDto);

    MovieDto findById(String id);

    MovieDto updateMovieById(String id, UpdateMovieRequestDto requestDto);

    void deleteById(String id);

    List<MovieDto> search(MovieSearchParameters searchParameters, Pageable pageable);
}
