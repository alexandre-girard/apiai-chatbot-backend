package fr.tm.ima.pocs.chatbot.rs.client;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "apiairesponse", type = "apiairesponse", shards = 1, replicas = 0, refreshInterval = "-1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiAiResponse {
	
    @Id
	String id;
	
    @Field(type=FieldType.Date)
	Date timestamp;
	
	ApiAiResult result;
	
	ApiAiStatus status;
	
	String sessionId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public ApiAiResult getResult() {
		return result;
	}

	public void setResult(ApiAiResult result) {
		this.result = result;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	

	public ApiAiStatus getStatus() {
		return status;
	}

	public void setStatus(ApiAiStatus status) {
		this.status = status;
	}
	
	

	public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "ApiAiResponse [id=" + id + ", timestamp=" + timestamp + ", result=" + result + ", status=" + status
                + ", sessionId=" + sessionId + "]";
    }
}
