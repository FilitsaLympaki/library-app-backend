package gr.aueb.cf.librarymanagementsystem.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetBookDto {

    private Long id;
    private String title;
    private String description;
    private Double rating;
    private String isbn;
    private Integer pages;
    private Double price;
    private Integer publicationYear;
    private String languageName;
    private String publisherName;
    private String genre;
    private String author;
    private String imageFileName;
}
