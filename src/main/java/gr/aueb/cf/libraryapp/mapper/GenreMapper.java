package gr.aueb.cf.libraryapp.mapper;

import gr.aueb.cf.libraryapp.dto.genre.GenreDto;
import gr.aueb.cf.libraryapp.model.staticdata.Genre;

public class GenreMapper {

    public static GenreDto mapGenreToDTO(Genre genre) {
        var dto = new GenreDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());

        return dto;
    }
}
