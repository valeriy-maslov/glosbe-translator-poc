package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.UserRegistrationResult;
import com.valeriymaslov.tlumacz.domain.models.User;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class InMemoryUserService implements UserRegistrationService {

    private final Set<User> users = new HashSet<>();

    @Override
    public UserRegistrationResult register(User newUser) {
        log.debug("Registering user: {}", newUser);
        UserRegistrationResult userRegistrationResult;
        if (users.add(newUser)) {
            userRegistrationResult = UserRegistrationResult.ok();
        } else {
            userRegistrationResult = UserRegistrationResult.fail("User already exists");
        }
        return userRegistrationResult;
    }

    @Override
    public User getUserById(String id) {
        log.debug("Getting user with id: {}", id);
        return users.stream().filter(user -> user.id().equals(id)).findFirst().orElse(null);
    }
}
