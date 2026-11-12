package life.youya.cqrs.command;

import life.youya.cqrs.command.annotation.CommandParameter;
import life.youya.cqrs.command.annotation.CommandResult;
import life.youya.cqrs.command.annotation.CommandType;

public interface AnnotatedCommand<P, R> extends Command<P, R> {

	@Override
	public default String getType() {
		CommandType type = this.getClass().getAnnotation(CommandType.class);
		return type.value();
	}

	@Override
	public default Class<?> getParameterClass() {
		for (Class<?> innerClass : this.getClass().getDeclaredClasses()) {
			CommandParameter paramAnno = innerClass.getAnnotation(CommandParameter.class);
			if (paramAnno != null) {
				return innerClass;
			}
		}
		throw new IllegalStateException("Command parameter class not specified");
	}

	@Override
	public default Class<?> getResultClass() {
		for (Class<?> innerClass : this.getClass().getDeclaredClasses()) {
			CommandResult resulAnno = innerClass.getAnnotation(CommandResult.class);
			if (resulAnno != null) {
				return innerClass;
			}
		}
		throw new IllegalStateException("Command result class not specified");
	}

}
