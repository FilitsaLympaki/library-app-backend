package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.core.exceptions.ResourceNotFoundException;
import gr.aueb.cf.libraryapp.dto.book.*;
import gr.aueb.cf.libraryapp.dto.genre.GenreDto;
import gr.aueb.cf.libraryapp.dto.language.LanguageDto;
import gr.aueb.cf.libraryapp.mapper.BookMapper;
import gr.aueb.cf.libraryapp.mapper.GenreMapper;
import gr.aueb.cf.libraryapp.mapper.LanguageMapper;
import gr.aueb.cf.libraryapp.model.Author;
import gr.aueb.cf.libraryapp.model.Book;
import gr.aueb.cf.libraryapp.model.Publisher;
import gr.aueb.cf.libraryapp.model.staticdata.Genre;
import gr.aueb.cf.libraryapp.model.staticdata.Language;
import gr.aueb.cf.libraryapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final LanguageRepository languageRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final ImageService imageService;


    @Transactional(readOnly = true)
    @Override
    public List<GenreDto> getGenres() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream()
                .map(GenreMapper::mapGenreToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<LanguageDto> getLanguages() {
        List<Language> languages = languageRepository.findAll();

        return languages.stream()
                .map(LanguageMapper::mapLanguageToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BookSearchResultDto> getBookSearchResults(BookFiltersDto filtersDto, Pageable pageable) {

        Page<Book> bookPageResult = bookRepository.findBooksWithFilters(filtersDto, pageable);

        List<BookSearchResultDto> bookPageResultDtos = bookPageResult.getContent()
                .stream()
                .map(bookMapper::mapBookToBookSearchResultDto)
                .toList();

        return new PageImpl<>(bookPageResultDtos, pageable, bookPageResult.getTotalElements());
    }

    @Transactional(readOnly = true)
    @Override
    public GetBookDto getBook(Long id) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Book with id " + id + " not found"));
        return bookMapper.mapBookToGetBookDto(book);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public GetBookDto createBook(CreateBookDto createBookDto)
            throws ResourceAlreadyExistsException, ResourceNotFoundException {

        //todo: create or update
        if (bookRepository.findByIsbn(createBookDto.getIsbn()).isPresent()) {
            throw new ResourceAlreadyExistsException("Book", "Book with ISBN " + createBookDto.getIsbn() + " already exists");
        }

        Author author = getOrCreateAuthor(createBookDto.getAuthorName());
        Publisher publisher = getOrCreatePublisher(createBookDto.getPublisherName());

        Genre genre = genreRepository.findById(createBookDto.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "Genre with id " + createBookDto.getGenreId() + "not found"));
        Language language = languageRepository.findById(createBookDto.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language", "Language with id " + createBookDto.getLanguageId() + " not found"));

        Book book = new Book();
        book.setTitle(createBookDto.getTitle());
        book.setDescription(createBookDto.getDescription());
        book.setRating(createBookDto.getRating());
        book.setPages(createBookDto.getPages());
        book.setPrice(createBookDto.getPrice());
        book.setPublicationYear(createBookDto.getPublicationYear());
        book.setIsbn(createBookDto.getIsbn());
        book.setGenre(genre);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setLanguage(language);

        String imageFileName = null;
        if (createBookDto.getImage() != null && !createBookDto.getImage().trim().isEmpty()) {
            imageFileName = imageService.saveBase64Image(createBookDto.getImage(), createBookDto.getTitle());
        }
        book.setImageFileName(imageFileName);

        bookRepository.save(book);
        return bookMapper.mapBookToGetBookDto(book);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public GetBookDto updateBook(Long id, UpdateBookDto updateBookDto)
            throws ResourceAlreadyExistsException, ResourceNotFoundException {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Book with id " + id + " not found"));
        //isbn
        if (!existingBook.getIsbn().equals(updateBookDto.getIsbn())) {
            if (bookRepository.findByIsbn(updateBookDto.getIsbn()).isPresent()) {
                throw new ResourceAlreadyExistsException("Book", "Book with ISBN " + updateBookDto.getIsbn() + " already exists");
            }
        }

        // title
        if (!existingBook.getTitle().equals(updateBookDto.getTitle())) {
            if (bookRepository.findByTitle(updateBookDto.getTitle()).isPresent()) {
                throw new ResourceAlreadyExistsException("Book", "Book with title " + updateBookDto.getTitle() + " already exists");
            }
        }

        //author
        Author author = getOrCreateAuthor(updateBookDto.getAuthorName());

        //publisher
        Publisher publisher = getOrCreatePublisher(updateBookDto.getPublisherName());

        //genre
        Genre genre = genreRepository.findById(updateBookDto.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre", "Genre with id " + updateBookDto.getGenreId() + " not found"));

        //language
        Language language = languageRepository.findById(updateBookDto.getLanguageId())
                .orElseThrow(() -> new ResourceNotFoundException("Language", "Language with id " + updateBookDto.getLanguageId() + " not found"));

        existingBook.setTitle(updateBookDto.getTitle());
        existingBook.setDescription(updateBookDto.getDescription());
        existingBook.setRating(updateBookDto.getRating());
        existingBook.setPages(updateBookDto.getPages());
        existingBook.setPrice(updateBookDto.getPrice());
        existingBook.setPublicationYear(updateBookDto.getPublicationYear());
        existingBook.setIsbn(updateBookDto.getIsbn());
        existingBook.setGenre(genre);
        existingBook.setAuthor(author);
        existingBook.setPublisher(publisher);
        existingBook.setLanguage(language);

        // Handle image update
        if (existingBook.getImageFileName() != null) {
            imageService.deleteImageFile(existingBook.getImageFileName());
        }

        if (updateBookDto.getImage() != null) {
            // Save new image
            String newImageFileName = imageService.saveBase64Image(updateBookDto.getImage(), updateBookDto.getTitle());
            existingBook.setImageFileName(newImageFileName);
        } else {
            // If image is null, delete existing image
            existingBook.setImageFileName(null);
        }

        bookRepository.save(existingBook);
        return bookMapper.mapBookToGetBookDto(existingBook);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteBook(Long id) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Book with id " + id + " not found"));

        if (book.getImageFileName() != null) {
            imageService.deleteImageFile(book.getImageFileName());
        }

        bookRepository.deleteById(id);
    }

//  ----------------  Auto-complete implementation  -----------------

    @Transactional(readOnly = true)
    @Override
    public List<String> getTitleSuggestions(String query, int size) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String trimmedQuery = query.trim();
        Pageable pageable = PageRequest.of(0, size);
        return bookRepository.findTitleSuggestions(trimmedQuery, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAuthorSuggestions(String query, int size) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String trimmedQuery = query.trim();
        Pageable pageable = PageRequest.of(0, size);
        return bookRepository.findAuthorSuggestions(trimmedQuery, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getPublisherSuggestions(String query, int size) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        String trimmedQuery = query.trim();
        Pageable pageable = PageRequest.of(0, size);
        return bookRepository.findPublisherSuggestions(trimmedQuery, pageable);

    }

    // --------------------------  HELPER METHODS    ---------------------------------------
    private Author getOrCreateAuthor(String authorName) {

        return authorRepository.findByNameIgnoreCase(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    return authorRepository.save(newAuthor);
                });
    }

    private Publisher getOrCreatePublisher(String publisherName) {
        return publisherRepository.findByNameIgnoreCase(publisherName)
                .orElseGet(() -> {
                    Publisher newPublisher = new Publisher(publisherName);
                    return publisherRepository.save(newPublisher);
                });
    }

}




