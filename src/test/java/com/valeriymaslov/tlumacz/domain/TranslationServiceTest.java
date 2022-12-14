package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.configuration.PlaywrightConfiguration;
import com.valeriymaslov.tlumacz.domain.dto.Language;
import com.valeriymaslov.tlumacz.domain.dto.TranslationRequest;
import com.valeriymaslov.tlumacz.domain.models.WordUseExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {
        PlaywrightConfiguration.class,
        GlosbeTranslationService.class
})
class TranslationServiceTest {

    @Autowired
    TranslationService service;

    @Test
    public void translateWordFromRuToEn() {

        // arrange
        var wordToTranslate = "сука";
        var sourceLanguage = Language.RU;
        var targetLanguage = Language.EN;
        var translateQuery = TranslationRequest.builder()
                .phrase(wordToTranslate)
                .from(sourceLanguage)
                .to(targetLanguage)
                .build();

        // act
        var result = service.translate(translateQuery);

        // assert
        assertThat(result).contains("bitch");
    }

    @Test
    public void translateWordFromEnToRu() {
        // arrange
        var wordToTranslate = "bitch";
        var sourceLanguage = Language.EN;
        var targetLanguage = Language.RU;
        var translateQuery = TranslationRequest.builder()
                .phrase(wordToTranslate)
                .from(sourceLanguage)
                .to(targetLanguage)
                .build();

        // act
        var result = service.translate(translateQuery);

        // assert
        assertThat(result).contains("сука");
    }

    @Test
    public void getExamplesForPhraseFromRuToEn() {

        // arrange
        var word = "сука";
        var sourceLanguage = Language.RU;
        var targetLanguage = Language.EN;
        var translateQuery = TranslationRequest.builder()
                .phrase(word)
                .from(sourceLanguage)
                .to(targetLanguage)
                .build();

        // act
        var result = service.getExamplesFor(translateQuery);

        // assert
        assertThat(result).contains(new WordUseExample(
                "И ещё, Джорджия... никогда не зови суку сукой.",
                "Oh, and, uh, Georgia... never call a bitch a bitch."
        ));
    }

    @Test
    public void getExamplesForPhraseFromEnToRu() {

        // arrange
        var word = "bitch";
        var sourceLanguage = Language.EN;
        var targetLanguage = Language.RU;
        var translateQuery = TranslationRequest.builder()
                .phrase(word)
                .from(sourceLanguage)
                .to(targetLanguage)
                .build();

        // act
        var result = service.getExamplesFor(translateQuery);

        // assert
        assertThat(result).contains(new WordUseExample(
                "Oh, and, uh, Georgia... never call a bitch a bitch.",
                "И ещё, Джорджия... никогда не зови суку сукой."
        ));
    }

}