package org.cyberrealm.tech.muvio.service;

import java.util.Set;
import org.cyberrealm.tech.muvio.model.Actor;

public interface ActorService {
    Set<Actor> getActors(int movieId);
}
