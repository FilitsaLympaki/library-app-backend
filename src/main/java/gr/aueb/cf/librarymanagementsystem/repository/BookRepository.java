package gr.aueb.cf.librarymanagementsystem.repository;

import gr.aueb.cf.librarymanagementsystem.model.Book;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);

    @Query("SELECT DISTINCT b.title FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY b.title")
    List<String> findTitleSuggestions(@Param("query") String query, Pageable pageable);

    @Query("SELECT DISTINCT a.name FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY a.name")
    List<String> findAuthorSuggestions(@Param("query") String query, Pageable pageable);

    @Query("SELECT DISTINCT p.name FROM Publisher p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY p.name")
    List<String> findPublisherSuggestions(@Param("query") String query, Pageable pageable);


}
