package gr.aueb.cf.libraryapp.dto.user;

import gr.aueb.cf.libraryapp.core.enums.Role;
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
