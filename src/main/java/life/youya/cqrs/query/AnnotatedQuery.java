package life.youya.cqrs.query;

import life.youya.cqrs.query.annotation.QuerySpecification;

public interface AnnotatedQuery<P, R> extends Query<P, R> {
	default String getType() {
		QuerySpecification spec = this.getClass().getAnnotation(QuerySpecification.class);
		return spec.type();
	}
	
	@SuppressWarnings("unchecked")
	default Class<P> getParameterClass() {
		QuerySpecification spec = this.getClass().getAnnotation(QuerySpecification.class);
		return (Class<P>) spec.parameterClass();
	}
	
	@SuppressWarnings("unchecked")
	default Class<R> getResultClass() {
		QuerySpecification spec = this.getClass().getAnnotation(QuerySpecification.class);
		return (Class<R>) spec.resultClass();
	}
}
