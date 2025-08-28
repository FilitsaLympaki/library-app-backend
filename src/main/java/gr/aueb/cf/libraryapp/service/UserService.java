package gr.aueb.cf.libraryapp.service;

import gr.aueb.cf.libraryapp.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.libraryapp.dto.user.RegisterRequestDto;
import gr.aueb.cf.libraryapp.dto.user.UserDto;

public interface UserService {

    UserDto register(RegisterRequestDto dto) throws ResourceAlreadyExistsException;
}
