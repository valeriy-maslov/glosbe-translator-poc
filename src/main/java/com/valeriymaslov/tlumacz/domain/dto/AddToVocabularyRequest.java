package com.valeriymaslov.tlumacz.domain.dto;

import com.valeriymaslov.tlumacz.domain.models.User;
import lombok.Builder;

import java.util.List;

@Builder
public record AddToVocabularyRequest(
        User user,
        Language language,
        List<String> phrases
) {
}
