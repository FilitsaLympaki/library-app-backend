package gr.aueb.cf.libraryapp.dto.dictionary;

import gr.aueb.cf.libraryapp.dto.genre.GenreDto;
import gr.aueb.cf.libraryapp.dto.language.LanguageDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DictionaryDto {

    private List<GenreDto> genres;
    private List<LanguageDto> languages;
}
