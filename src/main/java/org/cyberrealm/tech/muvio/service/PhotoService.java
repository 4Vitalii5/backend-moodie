package org.cyberrealm.tech.muvio.service;

import java.util.Set;
import org.cyberrealm.tech.muvio.model.Photo;

public interface PhotoService {
    Set<Photo> getPhotos(int movieId);
}
