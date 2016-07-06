package fr.tm.ima.pocs.chatbot.rs.client;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elasticsearch.common.lang3.StringUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.tm.ima.pocs.chatbot.controller.ChatMessage;
import fr.tm.ima.pocs.chatbot.rs.client.model.ApiAiEntity;
import fr.tm.ima.pocs.chatbot.service.Societaire;

@Service
public class ApiAiService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAiService.class);

    @Value("${apiai.url}")
    private String url;

    @Value("${apiai.client.token}")
    private String clientKey;

    /**
     * Client REST.
     * 
     * @return
     * 
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public ApiAiResponse getApiAiResponse(ChatMessage chatMessage) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target("https://api.api.ai/v1").path("/query?v=20150910");

        String message = StringUtils.stripAccents(chatMessage.getMessage());
        
        String post = "{ q: \"" + message + "\", sessionId: \"" + chatMessage.getSessionId()
                + "\", lang: \"en\"}";

        Response response = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientKey).post(Entity.json(post));

        return response.readEntity(ApiAiResponse.class);
    }

    public void postSocietaireContext(ChatMessage chatMessage, Societaire societaire) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target(url).path("/contexts?sessionId=" + chatMessage.getSessionId());

        String post = "[{\"name\": \"societaire\",\"lifespan\": 3, parameters : {\"nom\" : \"" + societaire.getNom()
                + "\", \"civilite\" : \"" + societaire.getCivilite() + "\", \"prenom\" : \"" + societaire.getPrenom()
                + "\"}}]";

        target.request().accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + clientKey)
                .post(Entity.json(post));
    }

    public ApiAiEntity getEntitie(String entityName) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target(url).path("/entities").path("/").path(entityName);

        Response response = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientKey).get();

        return response.readEntity(ApiAiEntity.class);
    }

    public String test() {
        LOGGER.info("Appel de la méthode test " + url + " " + clientKey);
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target(url).path("/entities");

        Response response = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientKey).get();

        LOGGER.info("Résultat de la méthode test : " + response.getStatus());
        return response.readEntity(String.class);
    }

    public void weather() {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        // https://console.api.ai/api/query?v=20150910

        WebTarget target = client.target("https://api.api.ai/v1/query?v=20150910");
        String post = "{\"q\":\"weather in London\",\"timezone\":\"2016-06-21T09:25:57+0200\",\"lang\":\"en\",\"sessionId\":\"32f8e882-d698-4ec4-90f2-785972ff08da\" ,\"resetContexts\":false}";

        Response response = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer 85920037be7a4d4f96caa1023fc22d79").post(Entity.json(post));

        System.out.println(response.readEntity(String.class));
    }

    public String getStringApiAiResponse(ChatMessage chatMessage) {
        Client client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        WebTarget target = client.target(url).path("/query?v=20150910");
        String post = "{ query: \"" + chatMessage.getMessage() + "\", sessionId: \"" + chatMessage.getSessionId()
                + "\", lang: \"en\" }";

        Response response = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + clientKey).post(Entity.json(post));

        return response.readEntity(String.class);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

}
