package dev.leandroschillreff.do_it.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.leandroschillreff.do_it.dto.UserDTO;
import dev.leandroschillreff.do_it.dto.UserResponseDTO;
import dev.leandroschillreff.do_it.entity.User;
import dev.leandroschillreff.do_it.exception.ResourceNotFoundException;
import dev.leandroschillreff.do_it.exception.UnauthorizedAccessException;
import dev.leandroschillreff.do_it.exception.UserAlreadyExistsException;
import dev.leandroschillreff.do_it.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND = "User not found with id: ";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    public UserResponseDTO createUser(UserDTO userCreationDTO) {
        if (userRepository.existsByEmail(userCreationDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }

        User user = new User();
        user.setName(userCreationDTO.getName());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserResponseDTO getUserById(Long userId, Long currentUserId) {
        if (!userId.equals(currentUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to access this resource.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(USER_NOT_FOUND + userId));
        return convertToDTO(user);
    }

    public UserResponseDTO updateUser(Long userId, UserDTO userCreationDTO, Long currentUserId) {
        if (!userId.equals(currentUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to update this resource.");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        if (!user.getEmail().equals(userCreationDTO.getEmail())
                && userRepository.existsByEmail(userCreationDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use.");
        }

        user.setName(userCreationDTO.getName());
        user.setEmail(userCreationDTO.getEmail());

        if (userCreationDTO.getPassword() != null && !userCreationDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    public void deleteUser(Long userId, Long currentUserId) {
        if (!userId.equals(currentUserId)) {
            throw new UnauthorizedAccessException("You are not authorized to delete this resource.");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(USER_NOT_FOUND + userId);
        }
        userRepository.deleteById(userId);
    }

    private UserResponseDTO convertToDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}