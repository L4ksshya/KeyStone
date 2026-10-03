package com.key_stone.service;

import java.util.List;
import com.key_stone.dto.LoginRequestDTO;
import com.key_stone.dto.LoginResponseDTO;

import org.springframework.stereotype.Service;

import com.key_stone.Entity.UserAuthentication;
import com.key_stone.dto.UserRequestDTO;
import com.key_stone.dto.UserResponseDTO;
import com.key_stone.exception.UserNotFoundException;
import com.key_stone.repository.UserAuthenticationRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.key_stone.exception.InvalidCredentialsException;
import java.util.Optional;
import com.key_stone.security.JwtService;

import com.key_stone.security.Role;

import com.key_stone.exception.UsernameAlreadyExistsException;

import com.key_stone.dto.ChangePasswordRequestDTO;

import com.key_stone.exception.UnauthorizedActionException;

import com.key_stone.dto.UpdateUserRequestDTO;


@Service
public class UserAuthenticationService {

	private final UserAuthenticationRepository userAuthenticationRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	

	// CREATE
	public UserResponseDTO createUser(UserRequestDTO request) {

	    if (userAuthenticationRepository
	            .findByUsername(request.getUsername())
	            .isPresent()) {

	        throw new UsernameAlreadyExistsException(
	                "Username already exists"
	        );
	    }

	    UserAuthentication user = new UserAuthentication();

	    user.setUsername(request.getUsername());
	    user.setRole(Role.USER);

	    user.setPassword(
	        passwordEncoder.encode(request.getPassword())
	    );

	    UserAuthentication savedUser =
	            userAuthenticationRepository.save(user);

	    return new UserResponseDTO(
	            savedUser.getId(),
	            savedUser.getUsername()
	    );
	}

    // GET ALL
    public List<UserResponseDTO> getAllUsers() {

        return userAuthenticationRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername()
                ))
                .toList();
    }

    // GET BY ID
    public UserResponseDTO getUserById(Long id) {

        UserAuthentication user =
                userAuthenticationRepository.findById(id)
                .orElseThrow(() ->
                new InvalidCredentialsException(
                    "Invalid username or password"
                )
            );
        return new UserResponseDTO(
                user.getId(),
                user.getUsername()
        );
    }

    // UPDATE
    public UserResponseDTO updateUser(
            Long id,
            UpdateUserRequestDTO request) {

        UserAuthentication existingUser =
                userAuthenticationRepository.findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException(
                        "User not found with id: " + id
                    )
                );
        
        if (!existingUser.getUsername().equals(request.getUsername())
                && userAuthenticationRepository
                        .findByUsername(request.getUsername())
                        .isPresent()) {

            throw new UsernameAlreadyExistsException(
                    "Username already exists"
            );
        }
        
        existingUser.setUsername(request.getUsername());



        UserAuthentication savedUser =
                userAuthenticationRepository.save(existingUser);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername()
        );
    }
    public void changePassword(
            Long id,
            String username,
            ChangePasswordRequestDTO request) {

        UserAuthentication user =
                userAuthenticationRepository.findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException(
                        "User not found with id: " + id
                    )
                );

        // Make sure the logged-in user is changing their own password
        if (!user.getUsername().equals(username)) {
            throw new UnauthorizedActionException(
                    "You can only change your own password"
            );
        }

        boolean currentPasswordMatches =
                passwordEncoder.matches(
                        request.getCurrentPassword(),
                        user.getPassword()
                );

        if (!currentPasswordMatches) {
            throw new InvalidCredentialsException(
                    "Current password is incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userAuthenticationRepository.save(user);
    }


    // DELETE
    public void deleteUser(Long id) {

        UserAuthentication user =
                userAuthenticationRepository.findById(id)
                .orElseThrow(() ->
                    new UserNotFoundException(
                        "User not found with id: " + id
                    )
                );

        userAuthenticationRepository.delete(user);
    }
    public LoginResponseDTO login(LoginRequestDTO request) {

        Optional<UserAuthentication> optionalUser =
                userAuthenticationRepository
                .findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        UserAuthentication user = optionalUser.get();

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                "Login successful",
                token
        );
    }
    
    public UserAuthenticationService(
            UserAuthenticationRepository userAuthenticationRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userAuthenticationRepository = userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    
}
