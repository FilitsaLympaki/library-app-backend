package gr.aueb.cf.librarymanagementsystem.mapper;

import gr.aueb.cf.librarymanagementsystem.dto.genre.GenreDto;
import gr.aueb.cf.librarymanagementsystem.model.static_data.Genre;

public class GenreMapper {

    public static GenreDto mapGenreToDTO(Genre genre) {
        var dto = new GenreDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
