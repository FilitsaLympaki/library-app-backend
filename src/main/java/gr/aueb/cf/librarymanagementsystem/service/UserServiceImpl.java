package gr.aueb.cf.librarymanagementsystem.service;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.librarymanagementsystem.dto.RegisterRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.UserDto;
import gr.aueb.cf.librarymanagementsystem.mapper.UserMapper;
import gr.aueb.cf.librarymanagementsystem.model.User;
import gr.aueb.cf.librarymanagementsystem.repository.UserRepository;
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
