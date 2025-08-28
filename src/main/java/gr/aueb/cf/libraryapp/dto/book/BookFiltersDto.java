package gr.aueb.cf.libraryapp.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookFiltersDto {

    private String title;
    private String author;
    private Long genreId;
    private Long languageId;
    private String publisher;
    private Integer publicationYear;

}
