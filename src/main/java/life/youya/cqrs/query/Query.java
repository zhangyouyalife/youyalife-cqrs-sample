package life.youya.cqrs.query;

public interface Query<P, R> extends QueryHandler<P, R> {
	String getType();
	
	Class<P> getParameterClass();
	
	Class<R> getResultClass();
}
