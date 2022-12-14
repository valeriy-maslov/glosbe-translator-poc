package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.ActionStatus;
import com.valeriymaslov.tlumacz.domain.dto.UserRegistrationResult;
import com.valeriymaslov.tlumacz.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class UserRegistrationServiceTest {

    UserRegistrationService service;

    @BeforeEach
    public void setup() {
        service = new InMemoryUserService();
    }

    @Test
    public void registerNewUser() {

        // arrange
        var newUser = new User("abc", "CoolDude67");

        // act
        var result = service.register(newUser);

        // assert
        assertThat(result).isInstanceOf(UserRegistrationResult.class)
                .hasFieldOrPropertyWithValue("actionStatus", ActionStatus.OK)
                .hasFieldOrPropertyWithValue("failMessage", null)
                .hasFieldOrPropertyWithValue("rootCause", null);
    }

    @Test
    public void getUserById() {

        // arrange
        var newUser = new User("abc", "CoolDude67");
        service.register(newUser);

        // act
        var createdUser = service.getUserById(newUser.id());

        // assert
        assertThat(createdUser).isInstanceOf(User.class)
                .hasFieldOrPropertyWithValue("id", newUser.id())
                .hasFieldOrPropertyWithValue("nickname", newUser.nickname());
    }

}