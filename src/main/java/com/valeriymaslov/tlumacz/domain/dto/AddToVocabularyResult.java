package com.valeriymaslov.tlumacz.domain.dto;

import java.util.List;

public record AddToVocabularyResult(
        ActionStatus actionStatus,
        List<String> saved,
        List<String> skipped,
        String failMessage,
        Throwable rootCause
) {
    public static AddToVocabularyResult ok(List<String> saved, List<String> skipped) {
        return new AddToVocabularyResult(ActionStatus.OK, saved, skipped, null, null);
    }

    public static AddToVocabularyResult fail(String message) {
        return new AddToVocabularyResult(ActionStatus.FAILED, null, null, message, null);
    }
}
