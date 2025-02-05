package org.cyberrealm.tech.muvio.dto;

public record MovieSearchParameters(
        String[] genres,
        String[] releaseDates,
        String[] voteAverages
) {
}
