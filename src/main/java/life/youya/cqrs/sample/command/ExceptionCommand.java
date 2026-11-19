package life.youya.cqrs.sample.command;

import org.springframework.stereotype.Component;

import life.youya.cqrs.command.AnnotatedCommand;
import life.youya.cqrs.command.CommandInstance;
import life.youya.cqrs.command.annotation.CommandParameter;
import life.youya.cqrs.command.annotation.CommandResult;
import life.youya.cqrs.command.annotation.CommandType;

@Component
@CommandType("Exp")
public class ExceptionCommand implements AnnotatedCommand<ExceptionCommand.Param, ExceptionCommand.Result> {
	@CommandParameter
	public static class Param {
		String bar;

		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}
	}
	
	@CommandResult
	public static class Result {
		String barResult;

		public String getBarResult() {
			return barResult;
		}

		public void setBarResult(String barResult) {
			this.barResult = barResult;
		}
	}

	@Override
	public void handle(CommandInstance<Param, Result> instance) throws Exception {
		throw new Exception("Exception occured.");
	}
}
