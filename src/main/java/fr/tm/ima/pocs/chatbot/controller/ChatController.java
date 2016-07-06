package fr.tm.ima.pocs.chatbot.controller;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.ima.pocs.chatbot.repository.ApiAiResponseRepository;
import fr.tm.ima.pocs.chatbot.repository.ApiAiResponseService;
import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResponse;
import fr.tm.ima.pocs.chatbot.rs.client.ApiAiService;
import fr.tm.ima.pocs.chatbot.rs.client.model.ApiAiEntity;
import fr.tm.ima.pocs.chatbot.service.Societaire;
import fr.tm.ima.pocs.chatbot.service.SocietaireService;

@RestController
public class ChatController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    ApiAiService apiAiService;

    @Autowired
    SocietaireService societaireService;

    @Autowired
    private ApiAiResponseService apiAiResponseService;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        return apiAiService.test();
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody ApiAiResponse chat(@RequestBody @Valid final ChatMessage chatMessage) {

        // Dans le cas d'une récupération du sociétaire, on fait une action
        // particulière pour récupérer les infos automatiquement
        if ("detection_societaire".equalsIgnoreCase(chatMessage.getAction())) {
            Societaire societaire = societaireService.getSocietaire(chatMessage.getMessage());

            apiAiService.postSocietaireContext(chatMessage, societaire);
        }

        ApiAiResponse response = apiAiService.getApiAiResponse(chatMessage);

        apiAiResponseService.save(response);

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (ApiAiResponse customer : apiAiResponseService.findAllSince10Min()) {
            // for (ApiAiResponse customer : apiAiResponseService.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // System.out.println("---------------------");
        // System.out.println(repository.findBySessionId("fErKPlmebVpub2kdjTctO1G5Jhg0HvTh"));

        return response;
    }

    @RequestMapping(value = "/entity/{name}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody ApiAiEntity getEntity(@PathVariable("name") String name) {
        return apiAiService.getEntitie(name);
    }
    
    
}
