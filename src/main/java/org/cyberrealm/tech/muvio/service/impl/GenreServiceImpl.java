package org.cyberrealm.tech.muvio.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.TmdbGenreMapper;
import org.cyberrealm.tech.muvio.model.GenreEntity;
import org.cyberrealm.tech.muvio.repository.GenreRepository;
import org.cyberrealm.tech.muvio.service.GenreService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final TmdbGenreMapper tmdbGenreMapper;
    private final TmdbClientService tmdbClientService;

    @Override
    public List<GenreEntity> loadGenresIntoRepository() {
        List<GenreEntity> genres = tmdbClientService.fetchGenres().stream()
                .filter(genre -> !genreRepository.existsById(String.valueOf(genre.getId())))
                .map(tmdbGenreMapper::toGenre)
                .toList();
        return genreRepository.saveAll(genres);
    }
}
