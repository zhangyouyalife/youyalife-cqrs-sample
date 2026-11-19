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

	@SuppressWarnings("unchecked")
	@Override
	public default Class<P> getParameterClass() {
		for (Class<?> innerClass : this.getClass().getDeclaredClasses()) {
			CommandParameter paramAnno = innerClass.getAnnotation(CommandParameter.class);
			if (paramAnno != null) {
				return (Class<P>) innerClass;
			}
		}
		throw new IllegalStateException("Command parameter class not specified");
	}

	@SuppressWarnings("unchecked")
	@Override
	public default Class<R> getResultClass() {
		for (Class<?> innerClass : this.getClass().getDeclaredClasses()) {
			CommandResult resulAnno = innerClass.getAnnotation(CommandResult.class);
			if (resulAnno != null) {
				return (Class<R>) innerClass;
			}
		}
		throw new IllegalStateException("Command result class not specified");
	}

}
