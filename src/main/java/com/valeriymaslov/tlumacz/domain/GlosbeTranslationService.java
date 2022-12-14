package com.valeriymaslov.tlumacz.domain;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.ElementHandle;
import com.valeriymaslov.tlumacz.domain.dto.Language;
import com.valeriymaslov.tlumacz.domain.dto.TranslationRequest;
import com.valeriymaslov.tlumacz.domain.models.WordUseExample;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GlosbeTranslationService implements TranslationService {

    private final Browser browser;

    private String getGlosbeUrl(Language from, Language to) {
        var fromLangCode = from.name().toLowerCase();
        var toLangCode = to.name().toLowerCase();
        return MessageFormat.format("https://en.glosbe.com/{0}/{1}/", fromLangCode, toLangCode);
    }

    @Override
    public List<String> translate(String word) {
        log.warn("Deprecated method was called in the translation service");
        return null;
    }

    @Override
    @SneakyThrows
    public List<String> translate(TranslationRequest request) {
        log.debug("Performing translation request: {}", request);
        try (var page = browser.newPage()) {
            page.navigate(getGlosbeUrl(request.from(), request.to()) + request.phrase());
            var elements = page.querySelectorAll("h3.translation__item__pharse");
            return elements.stream().map(ElementHandle::innerText).map(String::trim).toList();
        }
    }

    @Override
    public List<WordUseExample> getExamplesFor(String word) {
        log.warn("Deprecated method was called in the translation service");
        return null;
    }

    @Override
    public List<WordUseExample> getExamplesFor(TranslationRequest request) {
        log.debug("Performing example request: {}", request);
        try (var page = browser.newPage()) {
            page.navigate(getGlosbeUrl(request.from(), request.to()) + request.phrase());
            var translations = page.querySelectorAll("#tmem_first_examples div.dir-aware-pr-1").stream()
                    .map(ElementHandle::innerText).toList();
            var examples = page.querySelectorAll("#tmem_first_examples div.dir-aware-pl-1").stream()
                    .map(ElementHandle::innerText).toList();
            var result = new ArrayList<WordUseExample>(translations.size());
            for (var i = 0; i < translations.size(); i++) {
                result.add(new WordUseExample(translations.get(i).trim(), examples.get(i).trim()));
            }
            return result;
        }
    }
}
