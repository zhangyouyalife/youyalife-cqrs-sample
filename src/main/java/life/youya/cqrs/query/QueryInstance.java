package life.youya.cqrs.query;

public class QueryInstance<P, R> {
	Query<P, R> query;
	String id;
	P parameter;
	R result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Query<P, R> getQuery() {
		return query;
	}
	public void setQuery(Query<P, R> query) {
		this.query = query;
	}
	public P getParameter() {
		return parameter;
	}
	public void setParameter(P parameter) {
		this.parameter = parameter;
	}
	public R getResult() {
		return result;
	}
	public void setResult(R result) {
		this.result = result;
	}
	
	
}
