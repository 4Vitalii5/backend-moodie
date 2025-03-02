package org.cyberrealm.tech.muvio.model;

import lombok.Getter;

@Getter
public enum TvGenre {
    ACTION_ADVENTURE(10759, "Action & Adventure", "Бойовик та Пригоди"),
    ANIMATION(16, "Animation", "Анімація"),
    COMEDY(35, "Comedy", "Комедія"),
    CRIME(80, "Crime", "Кримінал"),
    DOCUMENTARY(99, "Documentary", "Документальний"),
    DRAMA(18, "Drama", "Драма"),
    FAMILY(10751, "Family", "Сімейний"),
    KIDS(10762, "Kids", "Дитячий"),
    MYSTERY(9648, "Mystery", "Містика"),
    NEWS(10763, "News", "Новини"),
    REALITY(10764, "Reality", "Реаліті"),
    SCI_FI_FANTASY(10765, "Sci-Fi & Fantasy", "Наукова фантастика та Фентезі"),
    SOAP(10766, "Soap", "Мильна опера"),
    TALK(10767, "Talk", "Ток-шоу"),
    WAR_POLITICS(10768, "War & Politics", "Війна та Політика"),
    WESTERN(37, "Western", "Вестерн");

    private final Integer id;
    private final String displayNameEn;
    private final String displayNameUk;

    TvGenre(int id, String displayNameEn, String displayNameUk) {
        this.id = id;
        this.displayNameEn = displayNameEn;
        this.displayNameUk = displayNameUk;
    }

    public static TvGenre fromId(Integer id) {
        for (TvGenre genre : values()) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid genre id: " + id);
    }
}
