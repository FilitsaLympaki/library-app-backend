package gr.aueb.cf.librarymanagementsystem.service;

import gr.aueb.cf.librarymanagementsystem.dto.DictionaryDto;
import gr.aueb.cf.librarymanagementsystem.dto.genre.GenreDto;
import gr.aueb.cf.librarymanagementsystem.dto.language.LanguageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionariesServiceIml implements DictionariesService {

    private final BookService bookService;

    @Override
    public DictionaryDto getDictionaries() {
        List<GenreDto> genreList = bookService.getGenres();
        List<LanguageDto> languageList = bookService.getLanguages();

        return new DictionaryDto(genreList, languageList);
    }
}
