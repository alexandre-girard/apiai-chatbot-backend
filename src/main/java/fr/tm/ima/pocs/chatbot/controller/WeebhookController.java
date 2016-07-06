package fr.tm.ima.pocs.chatbot.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.common.lang3.StringUtils;
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
    String webhook(@RequestBody final WebhookMessage message) {
        LOGGER.info("Appel de la méthode /webhook du controller " + message);
        
        String intentName = message.getResult().getMetadata().getIntentName();
        
        // Gestion du compteur
        int counter = 0;
        if(message.getResult().getParameters().containsKey("counter")){
            counter = Integer.parseInt(message.getResult().getParameters().get("counter"));
        }
        
        if(StringUtils.equalsIgnoreCase(intentName, "000_assistance_fallback")){
            return "{\"speech\":\"Je ne comprend pas pour la "+ counter + "...\", \"displayText\": \"reponse webhook ggg\"}";            
        }else{
            return "{\"speech\":\"Très bien je comprend, je vous met en relation. Pour information votre numéro de dossier 8989 \", \"displayText\": \"reponse webhook ggg\"}";
        }
    }
}
