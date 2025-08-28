package gr.aueb.cf.libraryapp.controller;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.core.exceptions.ResourceNotFoundException;
import gr.aueb.cf.libraryapp.dto.book.*;
import gr.aueb.cf.libraryapp.service.BookService;
import gr.aueb.cf.libraryapp.utils.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book Management", description = "Operations related to book management and search")
public class BookController {

    //todo: implement autocomplete for authors, publishers in different controllers

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Operation(
            summary = "Search books with filters",
            description = "Retrieves a paginated list of books based on various filter criteria",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Books Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookSearchResultDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @GetMapping("/search")
    public ResponseEntity<Page<BookSearchResultDto>> getBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "genreId", required = false) Long genreId,
            @RequestParam(value = "languageId", required = false) Long languageId,
            @RequestParam(value = "publisher", required = false) String publisher,
            @RequestParam(value = "publicationYear", required = false) Integer publicationYear,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String sortDirection
    ) {
        LOGGER.info("Search books - Start");

        BookFiltersDto dto = new BookFiltersDto(StringUtils.trim(title),
                StringUtils.trim(author),
                genreId,
                languageId,
                StringUtils.trim(publisher),
                publicationYear);

        Sort.Direction direction = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookSearchResultDto> books = bookService.getBookSearchResults(dto, pageable);

        LOGGER.info("Search books - End");
        return ResponseEntity.ok(books);
    }


    @Operation(
            summary = "Get book by ID",
            description = "Retrieves detailed information about a specific book",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetBookDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<GetBookDto> getBook(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOGGER.info("Get book with id: {} - Start", id);

        GetBookDto book = bookService.getBook(id);

        LOGGER.info("Get book with id: {} - End", id);
        return ResponseEntity.ok(book);
    }


    @Operation(
            summary = "Create a new book",
            description = "Creates a new book with the provided information",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Long.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Book already exists",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody @Valid CreateBookDto createBookDto) throws ResourceAlreadyExistsException, ResourceNotFoundException {
        LOGGER.info("Create book with title: {} - Start", createBookDto.getTitle());

        GetBookDto createdBook = bookService.createBook(createBookDto);

        LOGGER.info("Create book with title: {} - End", createBookDto.getTitle());
        return ResponseEntity.ok(createdBook.getId());
    }


    @Operation(
            summary = "Update book",
            description = "Updates an existing book with new information",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Book with updated data already exists",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id, @RequestBody @Valid UpdateBookDto updateBookDto)
            throws ResourceAlreadyExistsException, ResourceNotFoundException {
        LOGGER.info("Update book with id: {} - Start", id);

        bookService.updateBook(id, updateBookDto);

        LOGGER.info("Update book with id: {} - End", id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete book",
            description = "Permanently deletes a book from the system",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOGGER.info("Delete book with id: {} - Start", id);

        bookService.deleteBook(id);

        LOGGER.info("Delete book with id: {} - End", id);
        return ResponseEntity.ok().build();
    }


    //----------------------------- Auto-complete endpoints -----------------------------------
    @Operation(
            summary = "Get title suggestions",
            description = "Provides autocomplete suggestions for book titles based on partial input",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Title suggestions retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @GetMapping("/autocomplete/titles")
    public ResponseEntity<List<String>> getTitleSuggestions(
            @RequestParam("query") String query,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        LOGGER.info("Autocomplete titles for query: {} - start", query);

        List<String> suggestions = bookService.getTitleSuggestions(query, size);

        LOGGER.info("Autocomplete titles for query: {} - end", query);
        return ResponseEntity.ok(suggestions);
    }

    @Operation(
            summary = "Get author suggestions",
            description = "Provides autocomplete suggestions for author names based on partial input",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Author suggestions retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @GetMapping("/autocomplete/authors")
    public ResponseEntity<List<String>> getAuthorSuggestions(
            @RequestParam("query") String query,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        LOGGER.info("Autocomplete authors for query: {} - start", query);

        List<String> suggestions = bookService.getAuthorSuggestions(query, size);

        LOGGER.info("Autocomplete authors for query: {} - end", query);
        return ResponseEntity.ok(suggestions);
    }

    @Operation(
            summary = "Get publisher suggestions",
            description = "Provides autocomplete suggestions for publisher names based on partial input",
            security = @SecurityRequirement(name = "Bearer Authentication"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Publisher suggestions retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Authentication required",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @GetMapping("/autocomplete/publishers")
    public ResponseEntity<List<String>> getPublisherSuggestions(
            @RequestParam("query") String query,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        LOGGER.info("Autocomplete publishers for query: {} - start", query);

        List<String> suggestions = bookService.getPublisherSuggestions(query, size);

        LOGGER.info("Autocomplete publishers for query: {} - end", query);
        return ResponseEntity.ok(suggestions);
    }
}