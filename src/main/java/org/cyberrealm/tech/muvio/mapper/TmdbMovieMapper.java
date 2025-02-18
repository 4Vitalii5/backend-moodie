package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.core.Movie;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.MovieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TmdbMovieMapper {
    @Mapping(target = "id", expression = "java(String.valueOf(tmdbMovie.getId()))")
    MovieEntity toMovie(Movie tmdbMovie);
}
