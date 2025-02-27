package org.cyberrealm.tech.muvio.service;

import java.util.List;
import org.cyberrealm.tech.muvio.model.GenreEntity;

public interface GenreService {
    List<GenreEntity> loadGenresIntoRepository();
}
