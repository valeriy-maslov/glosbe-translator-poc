package com.valeriymaslov.tlumacz.domain.dto;

import lombok.Builder;

@Builder
public record TranslationRequest(
        String phrase,
        Language from,
        Language to
) {

}
