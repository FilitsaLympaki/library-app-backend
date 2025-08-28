package gr.aueb.cf.libraryapp.repository;

import gr.aueb.cf.libraryapp.dto.book.BookFiltersDto;
import gr.aueb.cf.libraryapp.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookRepositoryCustom {
    Page<Book> findBooksWithFilters(BookFiltersDto bookFiltersDto, Pageable pageable);
}
