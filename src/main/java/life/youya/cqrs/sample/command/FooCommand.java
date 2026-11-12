package life.youya.cqrs.sample.command;

import org.springframework.stereotype.Component;

import life.youya.cqrs.command.Command;
import life.youya.cqrs.command.CommandInstance;
import life.youya.cqrs.command.CommandStatus;

@Component
public class FooCommand implements Command<FooCommand.Param, FooCommand.Result> {
	
	public static class Param {
		String foo;

		public String getFoo() {
			return foo;
		}

		public void setFoo(String foo) {
			this.foo = foo;
		}
	}
	
	public static class Result {
		String fooResult;

		public String getFooResult() {
			return fooResult;
		}

		public void setFooResult(String fooResult) {
			this.fooResult = fooResult;
		}
	}

	@Override
	public void handle(CommandInstance<Param, Result> instance) {
		Result result = new Result();
		result.setFooResult("result");
		instance.setStatus(CommandStatus.SUCCESS);
	}

	@Override
	public String getType() {
		return "Foo";
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
