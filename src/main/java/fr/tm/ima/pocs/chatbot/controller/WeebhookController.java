package fr.tm.ima.pocs.chatbot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.common.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiContext;

@RestController
public class WeebhookController {
    private static final String COUNTER = "counter";
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WeebhookController.class);

    
    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    String webhook(@RequestBody final WebhookMessage message) {
        LOGGER.info("Appel de la méthode /webhook du controller");
        
        String intentName = message.getResult().getMetadata().getIntentName();
        
        
        
        // Gestion du compteur
        int counter = 0;
        List<ApiAiContext> apiAiContexts = message.getResult().getContexts();
        for (ApiAiContext apiAiContext : apiAiContexts) {
            if(apiAiContext.getParameters().containsKey(COUNTER)){
                counter = Integer.parseInt(apiAiContext.getParameters().get(COUNTER));
                
                System.out.println("Valeur counter " + counter);
                
                apiAiContext.getParameters().put(COUNTER, Integer.toString(counter +1));
            }            
        }
        // ddd
        WebhookResponse webhookResponse = new WebhookResponse();
        
        webhookResponse.setDisplayText("Reponse webhook");
        List<ApiAiContext> outApiAiContexts = new ArrayList<ApiAiContext>();
        ApiAiContext outApiAiContext = new ApiAiContext();
        outApiAiContext.setName("weather");
        outApiAiContext.setLifespan(2);
        
        outApiAiContexts.add(outApiAiContext);
        
        webhookResponse.setContextOut(apiAiContexts);
        
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            System.out.println("######################");
            System.out.println(mapper.writeValueAsString(webhookResponse));
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if(StringUtils.equalsIgnoreCase(intentName, "000_assistance_fallback")){
            //webhookResponse.setSpeech("Je ne comprend pas pour la "+ counter + "...");
            return "{\"speech\":\"Je ne comprend pas pour la "+ counter + "...\", \"displayText\": \"reponse webhook ggg\",\"contextOut\": [{\"name\":\"test-context\", \"lifespan\":2, \"parameters\":{\"counter\":\""+ counter + 1 +"\"}}]}";            
        }else{
            //webhookResponse.setSpeech("Très bien je comprend, je vous met en relation. Pour information votre numéro de dossier 8989");
            return "{\"speech\":\"Très bien je comprend, je vous met en relation. Pour information votre numéro de dossier 8989 \", \"displayText\": \"reponse webhook ggg\"}";
        }
        
        //return webhookResponse;
    }
}
