package fr.tm.ima.pocs.chatbot.controller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookMessage {
    
    @Id
    private String id;
    
    private ApiAiResult result;

    public ApiAiResult getResult() {
        return result;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResult(ApiAiResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WebhookMessage [result=" + result + "]";
    }
}
