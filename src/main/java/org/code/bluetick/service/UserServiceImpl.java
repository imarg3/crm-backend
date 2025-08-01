package org.code.bluetick.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code.bluetick.enums.ERole;
import org.code.bluetick.persistence.model.User;
import org.code.bluetick.persistence.repository.RoleRepository;
import org.code.bluetick.persistence.repository.UserRepository;
import org.code.bluetick.web.exception.UserAlreadyExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(final User user) {
        log.info("Registering new user account with email: {}", user.getEmail());
        if(emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is already an account with that email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName(ERole.ROLE_AGENT)));
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public void saveRegisteredUser(final User user) {
        log.debug("Saving registered user with email: {}", user.getEmail());
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(final String email) {
        log.debug("Finding user by email: {}", email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> 
            new UsernameNotFoundException("No user found with username: " + email));
        return user;
    }

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        log.debug("Retrieving all users with pagination");
        return userRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
