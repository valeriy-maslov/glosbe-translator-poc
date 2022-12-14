package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.*;
import com.valeriymaslov.tlumacz.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VocabularyServiceTest {

    VocabularyService service;

    @BeforeEach
    public void setup() {
        service = new InMemoryVocabularyService();
    }

    @Test
    public void addPhrases() {

        // arrange
        var vocabularyRequest = AddToVocabularyRequest.builder()
                .user(new User("abc", "CoolDude67"))
                .language(Language.EN)
                .phrases(List.of("cat", "dog", "fish"))
                .build();

        // act
        var result = service.addPhrases(vocabularyRequest);

        // assert
        assertThat(result).isInstanceOf(AddToVocabularyResult.class)
                .hasFieldOrPropertyWithValue("actionStatus", ActionStatus.OK)
                .hasFieldOrPropertyWithValue("failMessage", null)
                .hasFieldOrPropertyWithValue("rootCause", null);
        assertThat(result.saved()).containsExactlyInAnyOrder("cat", "dog", "fish");
    }

    @Test
    public void addPhrases_someExist() {

        // arrange
        var initialRequest = AddToVocabularyRequest.builder()
                .user(new User("abc", "CoolDude67"))
                .language(Language.EN)
                .phrases(List.of("cat", "fish"))
                .build();
        service.addPhrases(initialRequest);
        var vocabularyRequest = AddToVocabularyRequest.builder()
                .user(new User("abc", "CoolDude67"))
                .language(Language.EN)
                .phrases(List.of("cat", "dog", "fish"))
                .build();

        // act
        var result = service.addPhrases(vocabularyRequest);

        // assert
        assertThat(result).isInstanceOf(AddToVocabularyResult.class)
                .hasFieldOrPropertyWithValue("actionStatus", ActionStatus.OK)
                .hasFieldOrPropertyWithValue("failMessage", null)
                .hasFieldOrPropertyWithValue("rootCause", null);
        assertThat(result.saved()).containsExactlyInAnyOrder("dog");
        assertThat(result.skipped()).containsExactlyInAnyOrder("cat", "fish");
    }

}