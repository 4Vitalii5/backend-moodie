package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.movies.Cast;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.Actor;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TmdbActorMapper {
    Actor toActor(Cast cast);
}
