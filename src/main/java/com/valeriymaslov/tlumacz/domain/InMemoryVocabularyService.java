package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.AddToVocabularyResult;
import com.valeriymaslov.tlumacz.domain.dto.Language;
import com.valeriymaslov.tlumacz.domain.dto.UserRegistrationResult;
import com.valeriymaslov.tlumacz.domain.dto.AddToVocabularyRequest;
import com.valeriymaslov.tlumacz.domain.models.User;

import java.util.*;

public class InMemoryVocabularyService implements VocabularyService {

    private final Map<VocabularyKey, Set<String>> vocabularies = new HashMap<>();

    @Override
    public AddToVocabularyResult addPhrases(AddToVocabularyRequest vocabularyRequest) {
        var key = VocabularyKey.of(vocabularyRequest.user(), vocabularyRequest.language());
        var vocabulary = vocabularies.getOrDefault(
                key,
                new HashSet<>());
        var skipped = new ArrayList<String>();
        var saved = new ArrayList<String>();
        for (var phrase : vocabularyRequest.phrases()) {
            if (vocabulary.add(phrase)) {
                saved.add(phrase);
            } else {
                skipped.add(phrase);
            }
        }
        vocabularies.put(key, vocabulary);
        return AddToVocabularyResult.ok(saved, skipped);
    }

    private record VocabularyKey(
            String userId,
            String language
    ){
        static VocabularyKey of(User user, Language language) {
            var userId = user.id();
            var langCode = language.name().toLowerCase();
            return new VocabularyKey(userId, langCode);
        }
    }
}
