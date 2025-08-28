package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.dto.dictionary.DictionaryDto;
import gr.aueb.cf.libraryapp.dto.genre.GenreDto;
import gr.aueb.cf.libraryapp.dto.language.LanguageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictionariesServiceIml implements DictionariesService {

    private final BookService bookService;

    @Transactional(readOnly = true)
    @Override
    public DictionaryDto getDictionaries() {
        List<GenreDto> genreList = bookService.getGenres();
        List<LanguageDto> languageList = bookService.getLanguages();

        return new DictionaryDto(genreList, languageList);
    }
}
