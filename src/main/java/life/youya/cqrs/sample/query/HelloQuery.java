package life.youya.cqrs.sample.query;

import org.springframework.stereotype.Component;

import life.youya.cqrs.query.AnnotatedQuery;
import life.youya.cqrs.query.QueryInstance;
import life.youya.cqrs.query.annotation.QuerySpecification;

@Component
@QuerySpecification(
		type="Hello",
		parameterClass=HelloQuery.Param.class,
		resultClass=HelloQuery.Result.class)
public class HelloQuery implements AnnotatedQuery<HelloQuery.Param, HelloQuery.Result>{
	public static final class Param {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static final class Result {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@Override
	public void handle(QueryInstance<Param, Result> instance) {
		String name = instance.getParameter().getName();
		Result result = new Result();
		result.setMessage("Hello, " + name + "!");
		
		instance.setResult(result);
	}
}
