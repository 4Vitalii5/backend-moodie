package org.cyberrealm.tech.muvio.dto.movie;

public record MovieSearchParameters(
        String[] genres,
        String[] releaseDates,
        String[] voteAverages
) {
}
