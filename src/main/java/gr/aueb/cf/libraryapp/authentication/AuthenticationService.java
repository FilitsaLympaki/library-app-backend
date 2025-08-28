package gr.aueb.cf.libraryapp.authentication;

import gr.aueb.cf.libraryapp.core.exceptions.NotAuthorizedException;
import gr.aueb.cf.libraryapp.dto.auth.AuthenticationRequestDto;
import gr.aueb.cf.libraryapp.dto.auth.AuthenticationResponseDto;
import gr.aueb.cf.libraryapp.model.User;
import gr.aueb.cf.libraryapp.repository.UserRepository;
import gr.aueb.cf.libraryapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto dto)
            throws NotAuthorizedException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NotAuthorizedException("User", "User not authorized"));

        String token = jwtService.generateToken(authentication.getName(), user.getRole().name());
        return new AuthenticationResponseDto(token);
    }

}
