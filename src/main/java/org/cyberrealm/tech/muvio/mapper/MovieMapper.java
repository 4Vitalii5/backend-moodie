package org.cyberrealm.tech.muvio.mapper;

import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.dto.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.MovieDto;
import org.cyberrealm.tech.muvio.dto.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    Movie toMovie(CreateMovieRequestDto requestDto);

    void updateMovieFromDto(UpdateMovieRequestDto requestDto,
                            @MappingTarget Movie movie);
}
