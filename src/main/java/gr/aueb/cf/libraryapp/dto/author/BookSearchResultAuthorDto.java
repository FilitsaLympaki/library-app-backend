package gr.aueb.cf.libraryapp.dto.author;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookSearchResultAuthorDto {
    private Long id;
    private String name;
}
