package fr.tm.ima.pocs.chatbot.rs.client;

public class ApiAiMetaData {

    private String intentId;

    private boolean webhookUsed;

    private String intentName;

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public boolean isWebhookUsed() {
        return webhookUsed;
    }

    public void setWebhookUsed(boolean webhookUsed) {
        this.webhookUsed = webhookUsed;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

}
