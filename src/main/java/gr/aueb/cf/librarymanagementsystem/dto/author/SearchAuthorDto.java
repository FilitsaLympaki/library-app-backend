package gr.aueb.cf.librarymanagementsystem.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchAuthorDto {

    private String firstName;
    private String lastName;
    private String fullName; // Search by full name (first + last)
    private Date bornAfter;
    private Date bornBefore;
    private Date diedAfter;
    private Date diedBefore;
    private Boolean isAlive;
    private String bookTitle;
    private Integer minBooks;
    private Integer maxBooks;
}
