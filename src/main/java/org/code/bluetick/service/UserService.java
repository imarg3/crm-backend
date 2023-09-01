package org.code.bluetick.service;

import org.code.bluetick.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User registerNewUserAccount(User user);

    void saveRegisteredUser(User user);

    User findUserByEmail(String email);

    public Page<User> getAllUsers(Pageable pageable);
}
