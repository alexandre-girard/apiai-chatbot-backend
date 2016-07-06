package fr.tm.ima.pocs.chatbot.rs.client.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiAiEntity {
    String id;
    
    String name;
    
    List<ApiAiEntry> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApiAiEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ApiAiEntry> entries) {
        this.entries = entries;
    }
    
    
}
