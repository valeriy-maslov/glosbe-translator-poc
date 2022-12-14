package com.valeriymaslov.tlumacz.domain.dto;

public record UserRegistrationResult(
        ActionStatus actionStatus,
        String failMessage,
        Throwable rootCause
) {

    public static UserRegistrationResult ok() {
        return new UserRegistrationResult(ActionStatus.OK, null, null);
    }

    public static UserRegistrationResult fail(String message) {
        return new UserRegistrationResult(ActionStatus.FAILED, message, null);
    }
}
