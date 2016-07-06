package fr.tm.ima.pocs.chatbot.rs.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiAiFulfillment {
    String speech;

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    @Override
    public String toString() {
        return "ApiAiFulfillment [speech=" + speech + "]";
    }
}
