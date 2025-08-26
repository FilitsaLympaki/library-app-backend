package gr.aueb.cf.librarymanagementsystem.service;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.librarymanagementsystem.dto.RegisterRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.UserDto;

public interface UserService {

     UserDto register(RegisterRequestDto dto) throws ResourceAlreadyExistsException;
}
