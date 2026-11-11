package life.youya.cqrs.query;

import java.util.HashMap;
import java.util.Map;

public class QueryRegistry {
	Map<String, Query<Object, Object>> queries = new HashMap<>();
	
	public void addQuery(Query<Object, Object> query) {
		queries.put(query.getType(), query);
	}
	
	public Query<Object, Object> getQueryByType(String type) {
		return queries.get(type);
	}
}
