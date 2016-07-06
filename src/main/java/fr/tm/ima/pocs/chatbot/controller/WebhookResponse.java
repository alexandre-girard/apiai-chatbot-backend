package fr.tm.ima.pocs.chatbot.controller;

import java.util.List;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiContext;

public class WebhookResponse {
    private String speech;
    
    private String displayText;
    
    private List<ApiAiContext> contextOut;
    
    private String source = "IMAWebhook";

    public WebhookResponse() {
        super();
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }


    public List<ApiAiContext> getContextOut() {
        return contextOut;
    }

    public void setContextOut(List<ApiAiContext> contextOut) {
        this.contextOut = contextOut;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
