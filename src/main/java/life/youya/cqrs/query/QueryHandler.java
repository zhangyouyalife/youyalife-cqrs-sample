package life.youya.cqrs.query;

public interface QueryHandler<P, R> {
	void handle(QueryInstance<P, R> instance);
}
