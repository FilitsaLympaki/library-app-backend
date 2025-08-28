package gr.aueb.cf.libraryapp.mapper;

import gr.aueb.cf.libraryapp.core.enums.Role;
import gr.aueb.cf.libraryapp.dto.user.RegisterRequestDto;
import gr.aueb.cf.libraryapp.dto.user.UserDto;
import gr.aueb.cf.libraryapp.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    public User mapRegisterRequestDtoToUserDto(RegisterRequestDto dto) {

        return User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ADMIN)
                .build();
    }
}
