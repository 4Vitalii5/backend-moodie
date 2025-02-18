package org.cyberrealm.tech.muvio.mapper;

import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.dto.movie.CreateMovieRequestDto;
import org.cyberrealm.tech.muvio.dto.movie.MovieDto;
import org.cyberrealm.tech.muvio.dto.movie.UpdateMovieRequestDto;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface MovieMapper {
    MovieDto toDto(MovieEntity movie);

    MovieEntity toMovie(CreateMovieRequestDto requestDto);

    void updateMovieFromDto(UpdateMovieRequestDto requestDto,
                            @MappingTarget MovieEntity movie);
}
