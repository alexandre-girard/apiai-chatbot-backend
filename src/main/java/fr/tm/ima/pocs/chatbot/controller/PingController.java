package fr.tm.ima.pocs.chatbot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.ima.pocs.chatbot.ChatbotDemoApplication;

@RestController
public class PingController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatbotDemoApplication.class);

    @RequestMapping("/ping")
    String hello() {
        // LOGGER.info("Appel de la méthode /hello du controller");

        return "pong " + System.currentTimeMillis();
    }
}
