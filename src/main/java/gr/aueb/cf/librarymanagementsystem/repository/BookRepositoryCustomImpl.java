package gr.aueb.cf.librarymanagementsystem.repository;

import gr.aueb.cf.librarymanagementsystem.dto.book.BookFiltersDto;
import gr.aueb.cf.librarymanagementsystem.model.Author;
import gr.aueb.cf.librarymanagementsystem.model.Book;
import gr.aueb.cf.librarymanagementsystem.model.Publisher;
import gr.aueb.cf.librarymanagementsystem.model.static_data.Genre;
import gr.aueb.cf.librarymanagementsystem.model.static_data.Language;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Book> findBooksWithFilters(BookFiltersDto bookFiltersDto, Pageable pageable) {

        List<Book> books = getBooks(bookFiltersDto, pageable);
        Long count = countBooks(bookFiltersDto);

        return new PageImpl<>(books, pageable, count);
    }

    private List<Predicate> buildPredicates(
            BookFiltersDto bookFiltersDto,
            CriteriaBuilder cb,
            Join<Book, Author> authorJoin,
            Join<Book, Genre> genreJoin,
            Join<Book, Language> languageJoin,
            Join<Book, Publisher> publisherJoin,
            Root<Book> book

    ) {

        List<Predicate> predicates = new ArrayList<>();

        if (bookFiltersDto.getTitle() != null && !bookFiltersDto.getTitle().isEmpty()) {
            Predicate titlePredicate = cb.like(cb.lower(book.get("title")), "%" + bookFiltersDto.getTitle().toLowerCase() + "%");
            predicates.add(titlePredicate);
        }

        if (bookFiltersDto.getAuthor() != null && !bookFiltersDto.getAuthor().isEmpty()) {
            Predicate authorPredicate = cb.like(cb.lower(authorJoin.get("name")), "%" + bookFiltersDto.getAuthor().toLowerCase() + "%");
            predicates.add(authorPredicate);
        }

        if (bookFiltersDto.getGenreId() != null) {
            Predicate genrePredicate = cb.equal(genreJoin.get("id"), bookFiltersDto.getGenreId());
            predicates.add(genrePredicate);
        }

        if (bookFiltersDto.getLanguageId() != null) {
            Predicate languagePredicate = cb.equal(languageJoin.get("id"), bookFiltersDto.getLanguageId());
            predicates.add(languagePredicate);
        }

        if (bookFiltersDto.getPublisher() != null && !bookFiltersDto.getPublisher().isEmpty()) {
            Predicate publisherPredicate = cb.like(cb.lower(publisherJoin.get("name")), "%" + bookFiltersDto.getPublisher().toLowerCase() + "%");
            predicates.add(publisherPredicate);
        }

        if (bookFiltersDto.getPublicationYear() != null) {
            Predicate yearPredicate = cb.equal(book.get("publicationYear"), bookFiltersDto.getPublicationYear());
            predicates.add(yearPredicate);
        }

        return predicates;
    }

    private List<Book> getBooks(BookFiltersDto bookFiltersDto, Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);

        Join<Book, Author> authorJoin = book.join("author", JoinType.INNER);
        Join<Book, Genre> genreJoin = book.join("genre", JoinType.INNER);
        Join<Book, Language> languageJoin = book.join("language", JoinType.INNER);
        Join<Book, Publisher> publisherJoin = book.join("publisher", JoinType.INNER);

        List<Predicate> predicates = buildPredicates(
                bookFiltersDto,
                cb,
                authorJoin,
                genreJoin,
                languageJoin,
                publisherJoin,
                book);

        // where
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // order-by
        Sort.Order order = pageable.getSort().iterator().next();

        if (order.isAscending()) {
            query.orderBy(cb.asc(book.get(order.getProperty())));
        } else {
            query.orderBy(cb.desc(book.get(order.getProperty())));
        }

        // pagination
        TypedQuery<Book> typedQuery = em.createQuery(query);
        return typedQuery
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    private Long countBooks(BookFiltersDto bookFiltersDto) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        // count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Book> countRoot = countQuery.from(Book.class);

        Join<Book, Author> countAuthorJoin = countRoot.join("author", JoinType.INNER);
        Join<Book, Genre> countGenreJoin = countRoot.join("genre", JoinType.INNER);
        Join<Book, Language> countLanguageJoin = countRoot.join("language", JoinType.INNER);
        Join<Book, Publisher> countPublisherJoin = countRoot.join("publisher", JoinType.INNER);

        List<Predicate> countPredicates = buildPredicates(
                bookFiltersDto,
                cb,
                countAuthorJoin,
                countGenreJoin,
                countLanguageJoin,
                countPublisherJoin,
                countRoot);

        countQuery.select(cb.count(countRoot));

        if (!countPredicates.isEmpty()) {
            countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        }

        return em.createQuery(countQuery).getSingleResult();
    }
}
