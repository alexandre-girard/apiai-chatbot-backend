package fr.tm.ima.pocs.chatbot.rs.client;

public class ApiAiFulfillment {
    private String speech;

    private String source;

    private String displayText;

    private String data;

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiAiFulfillment [speech=" + speech + "]";
    }
}
