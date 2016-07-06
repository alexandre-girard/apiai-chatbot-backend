package fr.tm.ima.pocs.chatbot.rs.client;

public class ApiAiStatus {
	String code;
	
	String errorType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

    @Override
    public String toString() {
        return "ApiAiStatus [code=" + code + ", errorType=" + errorType + "]";
    }
}