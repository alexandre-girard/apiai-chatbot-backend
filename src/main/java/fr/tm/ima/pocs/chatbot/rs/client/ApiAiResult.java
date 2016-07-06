package fr.tm.ima.pocs.chatbot.rs.client;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiAiResult {

    String source;
    
    String resolvedQuery;
    
    String action;
    
    boolean actionIncomplete;
    
    ApiAiFulfillment fulfillment;

	ApiAiMetaData metadata; 
	
	Map<String, String> parameters;
	
	List<ApiAiContext> contexts;

	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResolvedQuery() {
        return resolvedQuery;
    }

    public void setResolvedQuery(String resolvedQuery) {
        this.resolvedQuery = resolvedQuery;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    
    
    public boolean isActionIncomplete() {
        return actionIncomplete;
    }

    public void setActionIncomplete(boolean actionIncomplete) {
        this.actionIncomplete = actionIncomplete;
    }

    public ApiAiFulfillment getFulfillment() {
        return fulfillment;
    }

    public void setFulfillment(ApiAiFulfillment fulfillment) {
        this.fulfillment = fulfillment;
    }


	public ApiAiMetaData getMetadata() {
		return metadata;
	}

	public void setMetadata(ApiAiMetaData metadata) {
		this.metadata = metadata;
	}
	
	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	

    public List<ApiAiContext> getContexts() {
        return contexts;
    }

    public void setContexts(List<ApiAiContext> contexts) {
        this.contexts = contexts;
    }

    @Override
    public String toString() {
        return "ApiAiResult [fulfillment=" + fulfillment + ", action=" + action + ", metadata=" + metadata
                + ", parameters=" + parameters + "]";
    }
	
}
