package org.cyberrealm.tech.muvio.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.dto.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.MovieDto;
import org.cyberrealm.tech.muvio.dto.MovieSearchParameters;
import org.cyberrealm.tech.muvio.dto.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.exception.EntityNotFoundException;
import org.cyberrealm.tech.muvio.mapper.MovieMapper;
import org.cyberrealm.tech.muvio.model.Movie;
import org.cyberrealm.tech.muvio.repository.movies.MovieRepository;
import org.cyberrealm.tech.muvio.service.MovieService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public MovieDto save(CreateMovieRequestDto requestDto) {
        Movie movie = movieMapper.toMovie(requestDto);
        movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public MovieDto findById(String id) {
        return movieMapper.toDto(getMovieById(id));
    }

    @Override
    public MovieDto updateMovieById(String id, UpdateMovieRequestDto requestDto) {
        Movie movie = getMovieById(id);
        movieMapper.updateMovieFromDto(requestDto, movie);
        movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<MovieDto> search(MovieSearchParameters searchParameters, Pageable pageable) {
        return List.of();
    }

    private Movie getMovieById(String id) {
        return movieRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find movie by id:" + id)
        );
    }
}
