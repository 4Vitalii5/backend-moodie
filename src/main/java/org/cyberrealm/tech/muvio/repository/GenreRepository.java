package org.cyberrealm.tech.muvio.repository;

import java.util.List;
import java.util.Set;
import org.cyberrealm.tech.muvio.model.GenreEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GenreRepository extends MongoRepository<GenreEntity, String> {
    @Query("{ 'id': { $in: ?0 } }")
    Set<GenreEntity> findGenresById(List<String> genreIds);
}
