package fr.tm.ima.pocs.chatbot.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResponse;

public interface ApiAiResponseRepository extends ElasticsearchRepository<ApiAiResponse, String> {

    public List<ApiAiResponse> findBySessionId(String sessionId);
}
