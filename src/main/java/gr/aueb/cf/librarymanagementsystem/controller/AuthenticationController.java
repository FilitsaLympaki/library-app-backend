package gr.aueb.cf.librarymanagementsystem.controller;

import gr.aueb.cf.librarymanagementsystem.authentication.AuthenticationService;
import gr.aueb.cf.librarymanagementsystem.core.exceptions.NotAuthorizedException;
import gr.aueb.cf.librarymanagementsystem.dto.AuthenticationRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.AuthenticationResponseDto;
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
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Login operation")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "User authentication",
            description = "Authenticates a user with username and password, returns JWT token on successful authentication",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Authentication successful - JWT token returned",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Authentication failed - Invalid username or password",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            example = "{\n  \"message\": \"Invalid username or password\"\n}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Valid AuthenticationRequestDto dto)
            throws NotAuthorizedException {

        LOGGER.info("Login user with username: {} - Start", dto.getUsername());
        
        AuthenticationResponseDto responseDto = authenticationService.authenticate(dto);
        LOGGER.info("Login user with username: {} - End", dto.getUsername());

        return ResponseEntity.ok(responseDto);
    }
}


