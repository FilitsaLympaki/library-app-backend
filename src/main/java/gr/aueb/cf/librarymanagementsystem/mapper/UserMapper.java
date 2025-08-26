package gr.aueb.cf.librarymanagementsystem.mapper;

import gr.aueb.cf.librarymanagementsystem.core.enums.Role;
import gr.aueb.cf.librarymanagementsystem.dto.RegisterRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.UserDto;
import gr.aueb.cf.librarymanagementsystem.model.User;
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
