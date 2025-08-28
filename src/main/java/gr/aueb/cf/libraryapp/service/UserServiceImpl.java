package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.dto.user.RegisterRequestDto;
import gr.aueb.cf.libraryapp.dto.user.UserDto;
import gr.aueb.cf.libraryapp.mapper.UserMapper;
import gr.aueb.cf.libraryapp.model.User;
import gr.aueb.cf.libraryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = {Exception.class})
    public UserDto register(RegisterRequestDto dto) throws ResourceAlreadyExistsException {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "User with username: " + dto.getUsername() + " already exists");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "User with email: " + dto.getEmail() + " already exists");
        }

        User user = userMapper.mapRegisterRequestDtoToUserDto(dto);
        userRepository.save(user);

        return userMapper.mapUserToUserDto(user);
    }

}
