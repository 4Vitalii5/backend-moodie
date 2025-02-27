package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.core.Movie;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.MovieDetails;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface MovieDetailsMapper {
    MovieDetails toMovieDetails(Movie tmdbMovie);
}
