package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.movies.Crew;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.Director;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TmdbDirectorMapper {
    Director toProducer(Crew crew);
}
