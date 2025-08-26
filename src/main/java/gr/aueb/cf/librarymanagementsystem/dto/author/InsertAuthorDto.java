package gr.aueb.cf.librarymanagementsystem.dto.author;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class InsertAuthorDto {
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @Size(max = 1000, message = "Biography cannot exceed 1000 characters")
    private String biography;

    @Past(message = "Birth date must be in the past")
    private Date birthDate;

    @Past(message = "Death date must be in the past")
    private Date deathDate;

    // Optional: Include initial books if creating author with books
    private Set<Long> bookIds;

}
