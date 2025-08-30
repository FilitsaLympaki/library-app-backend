package gr.aueb.cf.libraryapp.dto.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Valid
public class CreateBookDto {

    @NotBlank(message = "Book title cannot be empty.")
    private String title;

    @NotBlank(message = "Description cannot be empty.")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @DecimalMin(value = "0.0", message = "Rating must be non-negative")
    @DecimalMax(value = "5.0", message = "Rating cannot exceed 5.0")
    private Double rating;

    @NotBlank(message = "ISBN cannot be empty.")
    private String isbn;

    @NotNull
    @Positive(message = "Pages must be positive.")
    private Integer pages;

    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    private Double price;

    @NotNull(message = "Publish date is required.")
    private Integer publicationYear;

    @NotBlank(message = "Author is required.")
    private String authorName;

    @NotBlank(message = "Publisher is required.")
    private String publisherName;

    @NotNull(message = "Genre is required.")
    private Long genreId;

    @NotNull(message = "Language must be provided.")
    private Long languageId;

    private String image;
}
