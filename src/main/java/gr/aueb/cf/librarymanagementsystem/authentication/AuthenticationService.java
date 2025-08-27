package gr.aueb.cf.librarymanagementsystem.authentication;

import gr.aueb.cf.librarymanagementsystem.core.exceptions.NotAuthorizedException;
import gr.aueb.cf.librarymanagementsystem.dto.auth.AuthenticationRequestDto;
import gr.aueb.cf.librarymanagementsystem.dto.auth.AuthenticationResponseDto;
import gr.aueb.cf.librarymanagementsystem.model.User;
import gr.aueb.cf.librarymanagementsystem.repository.UserRepository;
import gr.aueb.cf.librarymanagementsystem.security.JwtService;
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
