package gr.aueb.cf.librarymanagementsystem.controller;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.ResourceAlreadyExistsException;
import gr.aueb.cf.librarymanagementsystem.dto.RegisterRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.UserDto;
import gr.aueb.cf.librarymanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Management", description = "Operations for user registration and account management")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account with the provided registration information. Email and username must be unique.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Forbidden - Invalid input data or validation errors",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequestDto dto)
            throws ResourceAlreadyExistsException {

        LOGGER.info("Register user with email: {} and username: {} - Start", dto.getEmail(), dto.getUsername());

        UserDto userDto = userService.register(dto);

        LOGGER.info("Register user with email: {} and username: {} - End", dto.getEmail(), dto.getUsername());
        return ResponseEntity.ok(userDto);
    }
}
