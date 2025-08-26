package gr.aueb.cf.librarymanagementsystem.dto;

import gr.aueb.cf.librarymanagementsystem.dto.genre.GenreDto;
import gr.aueb.cf.librarymanagementsystem.dto.language.LanguageDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DictionaryDto {

    private List<GenreDto> genres;
    private List<LanguageDto> languages;
}
