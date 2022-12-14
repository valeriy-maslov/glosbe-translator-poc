package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.UserRegistrationResult;
import com.valeriymaslov.tlumacz.domain.models.User;

public interface UserRegistrationService {
    UserRegistrationResult register(User newUser);

    User getUserById(String id);
}
