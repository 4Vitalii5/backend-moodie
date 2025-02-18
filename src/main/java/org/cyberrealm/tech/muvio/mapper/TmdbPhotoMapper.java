package org.cyberrealm.tech.muvio.mapper;

import info.movito.themoviedbapi.model.core.image.Artwork;
import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.Photo;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TmdbPhotoMapper {
    Photo toPhoto(Artwork artwork);
}
