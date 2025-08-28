package gr.aueb.cf.libraryapp.dto.book;

import gr.aueb.cf.libraryapp.dto.author.BookSearchResultAuthorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookSearchResultDto {

    private Long id;
    private String title;
    private String imageFileName;
    private BookSearchResultAuthorDto author;

}
