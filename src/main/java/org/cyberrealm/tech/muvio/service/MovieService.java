package org.cyberrealm.tech.muvio.service;

import java.util.List;
import org.cyberrealm.tech.muvio.dto.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.MovieDto;
import org.cyberrealm.tech.muvio.dto.MovieSearchParameters;
import org.cyberrealm.tech.muvio.dto.UpdateMovieRequestDto;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    List<MovieDto> findAll(Pageable pageable);

    MovieDto save(CreateMovieRequestDto requestDto);

    MovieDto findById(String id);

    MovieDto updateMovieById(String id, UpdateMovieRequestDto requestDto);

    void deleteById(String id);

    List<MovieDto> search(MovieSearchParameters searchParameters, Pageable pageable);
}
