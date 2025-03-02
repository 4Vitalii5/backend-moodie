package org.cyberrealm.tech.muvio.model;

import lombok.Getter;

@Getter
public enum MovieGenre {
    ACTION(28, "Action", "Бойовик"),
    ADVENTURE(12, "Adventure", "Пригоди"),
    ANIMATION(16, "Animation", "Анімація"),
    COMEDY(35, "Comedy", "Комедія"),
    CRIME(80, "Crime", "Кримінал"),
    DOCUMENTARY(99, "Documentary", "Документальний"),
    DRAMA(18, "Drama", "Драма"),
    FAMILY(10751, "Family", "Сімейний"),
    FANTASY(14, "Fantasy", "Фентезі"),
    HISTORY(36, "History", "Історія"),
    HORROR(27, "Horror", "Жахи"),
    MUSIC(10402, "Music", "Музика"),
    MYSTERY(9648, "Mystery", "Містика"),
    ROMANCE(10749, "Romance", "Романтика"),
    SCIENCE_FICTION(878, "Science Fiction", "Наукова фантастика"),
    TV_MOVIE(10770, "TV Movie", "Телефільм"),
    THRILLER(53, "Thriller", "Трилер"),
    WAR(10752, "War", "Війна"),
    WESTERN(37, "Western", "Вестерн");

    private final Integer id;
    private final String displayNameEn;
    private final String displayNameUk;

    MovieGenre(int id, String displayNameEn, String displayNameUk) {
        this.id = id;
        this.displayNameEn = displayNameEn;
        this.displayNameUk = displayNameUk;
    }

    public static MovieGenre fromId(Integer id) {
        for (MovieGenre genre : values()) {
            if (genre.getId().equals(id)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid genre id: " + id);
    }
}
