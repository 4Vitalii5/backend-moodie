package org.cyberrealm.tech.muvio.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.TmdbActorMapper;
import org.cyberrealm.tech.muvio.model.Actor;
import org.cyberrealm.tech.muvio.repository.ActorRepository;
import org.cyberrealm.tech.muvio.service.ActorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    public static final int MAX_NUMBER_OF_RECORDS = 10;
    private final ActorRepository actorRepository;
    private final TmdbActorMapper tmdbActorMapper;
    private final TmdbClientService tmdbClientService;

    @Override
    public Set<Actor> getActors(int movieId) {
        Set<Actor> actors = tmdbClientService.fetchMovieCast(movieId).stream()
                .limit(MAX_NUMBER_OF_RECORDS)
                .map(tmdbActorMapper::toActor)
                .collect(Collectors.toSet());
        actorRepository.saveAll(actors);
        return actors;
    }
}
