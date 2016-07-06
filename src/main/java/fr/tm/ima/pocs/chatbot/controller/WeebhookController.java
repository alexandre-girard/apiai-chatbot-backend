package fr.tm.ima.pocs.chatbot.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeebhookController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeebhookController.class);

    
    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    String webhook(@RequestBody final String message) {
        LOGGER.info("Appel de la méthode /webhook du controller " + message);

        return "{\"speech\":\"Très bien je comprend, je vous met en relation. Pour information votre numéro de dossier 78978977 \", \"displayText\": \"reponse webhook ggg\"}";
    }
}
