package com.valeriymaslov.tlumacz.domain;

import com.valeriymaslov.tlumacz.domain.dto.AddToVocabularyRequest;
import com.valeriymaslov.tlumacz.domain.dto.AddToVocabularyResult;
import com.valeriymaslov.tlumacz.domain.dto.UserRegistrationResult;

public interface VocabularyService {
    AddToVocabularyResult addPhrases(AddToVocabularyRequest vocabularyRequest);
}
