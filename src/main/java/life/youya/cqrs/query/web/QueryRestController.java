package life.youya.cqrs.query.web;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import life.youya.cqrs.query.Query;
import life.youya.cqrs.query.QueryInstance;
import life.youya.cqrs.query.QueryRegistry;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/query")
public class QueryRestController {
	
	QueryRegistry queryRegistry;
	
	@Autowired
	JsonMapper jsonMapper;
	
	@Autowired
	public ApplicationContext applicationContext;
	
	@PostMapping
	public ResponseEntity<?> query(@RequestBody QueryBody queryMessage) {
		
		String queryName = queryMessage.getType();
		
		String queryId = queryMessage.getId();
		if (StringUtils.isBlank(queryId)) {
			
			queryId = generateQueryId();
			queryMessage.setId(queryId);
			
			System.out.println("generate query id: " + queryId);
		}
		
		Query<Object, Object> query = queryRegistry.getQueryByType(queryName);
				
		Object parameter = jsonMapper.readerFor(query.getParameterClass()).readValue(queryMessage.getParameter());
		
		QueryInstance<Object, Object> instance = new QueryInstance<>();
		instance.setQuery(query);
		instance.setId(queryId);
		instance.setParameter(parameter);
		query.handle(instance);
		queryMessage.setResult(instance.getResult());
		
		return ResponseEntity.ok(queryMessage);
	}
	
	String generateQueryId() {
		return UUID.randomUUID().toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() {
		
		queryRegistry = new QueryRegistry();
		
		Map<String, Query> queries = applicationContext.getBeansOfType(Query.class);
		
		for (Query<Object, Object> q : queries.values()) {
			queryRegistry.addQuery(q);
		}
	}
}
