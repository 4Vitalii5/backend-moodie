package org.cyberrealm.tech.muvio.mapper;

import org.cyberrealm.tech.muvio.config.MapperConfig;
import org.cyberrealm.tech.muvio.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ReviewMapper {
    @Mapping(source = "authorDetails.avatarPath", target = "avatarPath")
    @Mapping(source = "authorDetails.rating", target = "rating")
    Review toReview(info.movito.themoviedbapi.model.core.Review review);
}
