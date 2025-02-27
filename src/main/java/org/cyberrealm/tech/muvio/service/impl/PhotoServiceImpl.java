package org.cyberrealm.tech.muvio.service.impl;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.cyberrealm.tech.muvio.mapper.TmdbPhotoMapper;
import org.cyberrealm.tech.muvio.model.Photo;
import org.cyberrealm.tech.muvio.repository.PhotoRepository;
import org.cyberrealm.tech.muvio.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    public static final int MAX_NUMBER_OF_RECORDS = 10;
    private final PhotoRepository photoRepository;
    private final TmdbPhotoMapper tmdbPhotoMapper;
    private final TmdbClientService tmdbClientService;

    @Override
    public Set<Photo> getPhotos(int movieId) {
        return tmdbClientService.fetchMoviePhotos(movieId).stream()
                .limit(MAX_NUMBER_OF_RECORDS)
                .map(tmdbPhotoMapper::toPhoto)
                .map(photoRepository::save)
                .collect(Collectors.toSet());
    }
}
