package com.valeriymaslov.tlumacz.bot;

import com.valeriymaslov.tlumacz.domain.TranslationService;
import com.valeriymaslov.tlumacz.domain.dto.Language;
import com.valeriymaslov.tlumacz.domain.dto.TranslationRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @deprecated Because it is a prototype
 */
@SuppressWarnings("FieldCanBeLocal")
@Component
@Slf4j
@Deprecated(forRemoval = true)
public class TelegramBotPocController {

    private final TelegramBotsApi botsApi;
    private final BotSession botSession;

    @Setter(onMethod_ = {@Autowired})
    private TranslationService translationService;

    @SneakyThrows
    public TelegramBotPocController(@Value("${bot.telegram.token}") String token) {
        botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botSession = botsApi.registerBot(new Bot(token));
        log.info("Telegram bot registered, session enabled: {}", botSession.isRunning());
    }

    @RequiredArgsConstructor
    public class Bot extends TelegramLongPollingBot {

        private final String token;
        private static final String USERNAME = "tlumaczdevbot";

        @Override
        public String getBotUsername() {
            return USERNAME;
        }

        @Override
        public String getBotToken() {
            return token;
        }

        @SneakyThrows
        @Override
        public void onUpdateReceived(Update update) {
            log.debug("Telegram message received: {}", update);
            var user = update.getMessage().getFrom();

            if (update.getMessage().getText().equals("/start")) {
                var build = SendMessage.builder().text("Hello!").chatId(user.getId()).build();
                this.execute(build);
            } else if (update.getMessage().getText().startsWith("/translate")) {
                var phrase = update.getMessage().getText().split(" ", 2)[1];
                var request = TranslationRequest.builder()
                        .from(Language.RU).to(Language.EN).phrase(phrase).build();
                var translate = translationService.translate(request);
                var response = SendMessage.builder().text(String.join(", ", translate)).chatId(user.getId()).build();
                this.execute(response);
            } else {
                var build = SendMessage.builder().text("I don't know what it is!").chatId(user.getId()).build();
                this.execute(build);
            }
        }
    }
}
