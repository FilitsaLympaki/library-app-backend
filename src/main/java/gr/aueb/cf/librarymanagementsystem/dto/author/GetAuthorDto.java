package gr.aueb.cf.librarymanagementsystem.dto.author;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class GetAuthorDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String biography;
    private Date birthDate;
    private Date deathDate;
    private Set<String> bookTitles;
    private Integer bookCount; // Number of books by this author
}
