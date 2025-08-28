package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.core.exceptions.ResourceNotFoundException;
import gr.aueb.cf.libraryapp.dto.book.*;
import gr.aueb.cf.libraryapp.dto.genre.GenreDto;
import gr.aueb.cf.libraryapp.dto.language.LanguageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    List<GenreDto> getGenres();

    List<LanguageDto> getLanguages();

    Page<BookSearchResultDto> getBookSearchResults(BookFiltersDto filtersDto, Pageable pageable);

    GetBookDto getBook(Long id) throws ResourceNotFoundException;

    GetBookDto createBook(CreateBookDto createBookDto) throws ResourceAlreadyExistsException, ResourceNotFoundException;

    GetBookDto updateBook(Long id, UpdateBookDto updateBookDto) throws ResourceAlreadyExistsException, ResourceNotFoundException;

    void deleteBook(Long id) throws ResourceNotFoundException;

    // -----------   Auto-complete suggestions  ----------------------
    List<String> getTitleSuggestions(String query, int limit);

    List<String> getAuthorSuggestions(String query, int limit);

    List<String> getPublisherSuggestions(String query, int limit);
}
