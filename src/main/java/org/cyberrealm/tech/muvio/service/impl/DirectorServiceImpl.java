package org.cyberrealm.tech.muvio.service.impl;

import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.TmdbDirectorMapper;
import org.cyberrealm.tech.muvio.model.Director;
import org.cyberrealm.tech.muvio.repository.DirectorRepository;
import org.cyberrealm.tech.muvio.service.DirectorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final TmdbDirectorMapper tmdbDirectorMapper;
    private final TmdbClientService tmdbClientService;

    @Override
    public Director getDirector(int movieId) {
        return tmdbClientService.fetchDirector(movieId)
                .map(tmdbDirectorMapper::toProducer)
                .map(directorRepository::save)
                .orElse(null);
    }
}
