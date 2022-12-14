package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.TranslationRequest;
import com.valeriymaslov.tlumacz.domain.models.WordUseExample;

import java.util.List;

public interface TranslationService {

    /**
     * @deprecated Part of POC with Playwright
     */
    @Deprecated
    List<String> translate(String word);

    List<String> translate(TranslationRequest request);

    /**
     * @deprecated Part of POC with Playwright
     */
    @Deprecated
    List<WordUseExample> getExamplesFor(String word);


    List<WordUseExample> getExamplesFor(TranslationRequest word);
}
