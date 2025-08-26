package gr.aueb.cf.librarymanagementsystem.dto.author;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class UpdateAuthorDto {

    @NotNull(message = "Author ID is required")
    private Long id;

    private String firstName;
    private String lastName;

    @Size(max = 1000, message = "Biography cannot exceed 1000 characters")
    private String biography;

    @Past(message = "Birth date must be in the past")
    private Date birthDate;

    @Past(message = "Death date must be in the past")
    private Date deathDate;
}
