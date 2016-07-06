package fr.tm.ima.pocs.chatbot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.tm.ima.pocs.chatbot.repository.ApiAiResponseService;
import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResponse;

@RestController
public class SupervisorController {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SupervisorController.class);

    @Autowired
    private ApiAiResponseService apiAiResponseService;

    @RequestMapping("/supervisor/findLastMessage")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<ApiAiResponse>> findLastMessage() {
        LOGGER.info("Appel de la méthode /supervisor/findLastMessage");

        Iterable<ApiAiResponse> list = apiAiResponseService.findAllSince10Min();

        Map<String, List<ApiAiResponse>> mappedApiAiResponses = new HashMap<String, List<ApiAiResponse>>();
        for (ApiAiResponse apiAiResponse : list) {
            String key = apiAiResponse.getSessionId();
            List<ApiAiResponse> apiAiResponses;
            if (mappedApiAiResponses.containsKey(key)) {
                apiAiResponses = mappedApiAiResponses.get(key);
            } else {
                apiAiResponses = new ArrayList<ApiAiResponse>();
            }
            apiAiResponses.add(apiAiResponse);

            mappedApiAiResponses.put(apiAiResponse.getSessionId(), apiAiResponses);
        }

        return mappedApiAiResponses;
    }
    
    @RequestMapping("/supervisor/usersConnected")
    @Produces(MediaType.APPLICATION_JSON)
    public long countConnectedUser() {
        return apiAiResponseService.countConnectedUser();
    }
}
