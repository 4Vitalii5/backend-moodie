package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.core.Movie;
import java.util.Set;
import java.util.stream.Collectors;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.dto.movie.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieDto;
import org.cyberrealm.tech.muvio.dto.movie.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.cyberrealm.tech.muvio.model.MovieGenre;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface MovieMapper {
    MovieDto toDto(MovieEntity movie);

    MovieEntity toMovie(CreateMovieRequestDto requestDto);

    @Mapping(target = "id", expression = "java(String.valueOf(tmdbMovie.getId()))")
    MovieEntity toMovie(Movie tmdbMovie);

    void updateMovieFromDto(UpdateMovieRequestDto requestDto,
                            @MappingTarget MovieEntity movie);

    @AfterMapping
    default Set<MovieGenre> mapToGenre(Set<Integer> genreIds) {
        return genreIds.stream()
                .map(MovieGenre::fromId) // Assuming you have a method to convert ID to enum
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default Set<Integer> mapToIds(Set<MovieGenre> genres) {
        return genres.stream()
                .map(MovieGenre::getId) // Assuming you have a method getId() in enum
                .collect(Collectors.toSet());
    }
}
