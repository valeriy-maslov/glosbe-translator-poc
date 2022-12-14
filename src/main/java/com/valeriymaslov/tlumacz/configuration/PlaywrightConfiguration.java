package com.valeriymaslov.tlumacz.configuration;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class PlaywrightConfiguration {

    @Bean
    public Playwright playwright() {
        return Playwright.create();
    }

    @Bean
    public Browser browser(Playwright playwright) {
        var options = new BrowserType.LaunchOptions();
        options.setChannel("chrome");
        options.setArgs(List.of(
                "--disable-gpu",
                "--window-size=1920,1080"
        ));
        var browser = playwright.chromium().launch(options);
        log.info("Browser connection: {}", browser.isConnected());
        return browser;
    }


}
