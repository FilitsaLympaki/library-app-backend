package gr.aueb.cf.librarymanagementsystem.dto;

import gr.aueb.cf.librarymanagementsystem.core.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Role role;
}
