package fr.tm.ima.pocs.chatbot.repository;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.cardinality.InternalCardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import fr.tm.ima.pocs.chatbot.rs.client.ApiAiResponse;

@Service
public class ApiAiResponseService {

    @Autowired
    private ApiAiResponseRepository repository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public void save(ApiAiResponse apiAiResponse) {
        repository.save(apiAiResponse);
    }

    public Iterable<ApiAiResponse> findAll() {
        return repository.findAll();
    }

    public long countConnectedUser() {
        QueryBuilder query = QueryBuilders.rangeQuery("timestamp").gte("now-1m");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query)
                .addAggregation(AggregationBuilders.cardinality("distinct_session").field("sessionId")).build();

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        InternalCardinality res = aggregations.get("distinct_session");
        return res.getValue();
    }

    public Iterable<ApiAiResponse> findAllSince10Min() {
        QueryBuilder query = QueryBuilders.rangeQuery("timestamp").gte("now-30m");
        return repository.search(query);
    }

}
