package gr.aueb.cf.libraryapp.mapper;

import gr.aueb.cf.libraryapp.dto.author.BookSearchResultAuthorDto;
import gr.aueb.cf.libraryapp.dto.book.BookSearchResultDto;
import gr.aueb.cf.libraryapp.dto.book.CreateBookDto;
import gr.aueb.cf.libraryapp.dto.book.GetBookDto;
import gr.aueb.cf.libraryapp.dto.book.UpdateBookDto;
import gr.aueb.cf.libraryapp.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private static final String IMAGE_BASE_URL = "http://localhost:8080/images/";

    public BookSearchResultDto mapBookToBookSearchResultDto(Book book) {
        var dto = new BookSearchResultDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        // Map image with full URL
        dto.setImageFileName(book.getImageFileName() != null ?
                IMAGE_BASE_URL + book.getImageFileName() : null);

        var authorDto = new BookSearchResultAuthorDto();

        authorDto.setId(book.getAuthor().getId());
        authorDto.setName(book.getAuthor().getName());
        dto.setAuthor(authorDto);

        return dto;
    }

    public GetBookDto mapBookToGetBookDto(Book book) {
        var dto = new GetBookDto();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());
        dto.setIsbn(book.getIsbn());
        dto.setPages(book.getPages());
        dto.setPrice(book.getPrice());
        dto.setPublicationYear(book.getPublicationYear());

        // Map image with full URL
        dto.setImageFileName(book.getImageFileName() != null ?
                IMAGE_BASE_URL + book.getImageFileName() : null);

        if (book.getLanguage() != null) {
            dto.setLanguageName(book.getLanguage().getName());
        }

        if (book.getPublisher() != null) {
            dto.setPublisherName(book.getPublisher().getName());
        }

        if (book.getGenre() != null) {
            dto.setGenre(book.getGenre().getName());
        }

        if (book.getAuthor() != null) {
            dto.setAuthor(book.getAuthor().getName());
        }

        return dto;
    }

    public Book createEntityFromDTO(CreateBookDto dto) {

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setRating(dto.getRating());
        book.setIsbn(dto.getIsbn());
        book.setPages(dto.getPages());
        book.setPrice(dto.getPrice());
        book.setPublicationYear(dto.getPublicationYear());

        return book;
    }


    public void updateEntityFromDTO(Book book, UpdateBookDto updateBookDTO) {

        if (updateBookDTO == null && book == null) return;

        if (updateBookDTO.getTitle() != null) {
            book.setTitle(updateBookDTO.getTitle());
        }

        if (updateBookDTO.getDescription() != null) {
            book.setDescription(updateBookDTO.getDescription());
        }

        if (updateBookDTO.getRating() != null) {
            book.setRating(updateBookDTO.getRating());
        }

        if (updateBookDTO.getIsbn() != null) {
            book.setIsbn(updateBookDTO.getIsbn());
        }

        if (updateBookDTO.getPages() != null) {
            book.setPages(updateBookDTO.getPages());
        }

        if (updateBookDTO.getPrice() != null) {
            book.setPrice(updateBookDTO.getPrice());
        }

        if (updateBookDTO.getPublicationYear() != null) {
            book.setPublicationYear(updateBookDTO.getPublicationYear());
        }
    }

}
