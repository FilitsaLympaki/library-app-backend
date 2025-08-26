package gr.aueb.cf.librarymanagementsystem.repository;

import gr.aueb.cf.librarymanagementsystem.dto.book.BookFiltersDto;
import gr.aueb.cf.librarymanagementsystem.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookRepositoryCustom {
    Page<Book> findBooksWithFilters(BookFiltersDto bookFiltersDto, Pageable pageable);
}
