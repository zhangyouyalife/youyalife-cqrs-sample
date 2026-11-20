package life.youya.cqrs.query.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface QuerySpecification {
	public String type();
	public Class<?> parameterClass();
	public Class<?> resultClass();
}
