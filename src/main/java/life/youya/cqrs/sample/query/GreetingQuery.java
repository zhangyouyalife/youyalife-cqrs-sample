package life.youya.cqrs.sample.query;

import org.springframework.stereotype.Component;

import life.youya.cqrs.query.Query;
import life.youya.cqrs.query.QueryInstance;
import life.youya.cqrs.sample.query.GreetingQuery.Param;
import life.youya.cqrs.sample.query.GreetingQuery.Result;

@Component
public class GreetingQuery implements Query<Param, Result> {
	
	public static class Param {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class Result {
		String greeting;

		public String getGreeting() {
			return greeting;
		}

		public void setGreeting(String greeting) {
			this.greeting = greeting;
		}
	}
	
	public void handle(QueryInstance<Param, Result> instance) {
		Result result = new Result();
		result.greeting = "Hello, " +  (instance.getParameter().getName() == null ? "World" : instance.getParameter().getName()) + "!";
		instance.setResult(result);
	}

	@Override
	public String getType() {
		return "Greeting";
	}

	@Override
	public Class<Param> getParameterClass() {
		return Param.class;
	}

	@Override
	public Class<Result> getResultClass() {
		return Result.class;
	}
}
