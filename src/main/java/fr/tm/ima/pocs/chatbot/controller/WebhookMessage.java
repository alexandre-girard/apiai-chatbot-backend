package fr.tm.ima.pocs.chatbot.controller;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookMessage {
    
    private ApiAiResult result;

    public ApiAiResult getResult() {
        return result;
    }

    public void setResult(ApiAiResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WebhookMessage [result=" + result + "]";
    }
}
