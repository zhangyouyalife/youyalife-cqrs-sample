package life.youya.cqrs.command;

public interface Command<P, R> extends CommandHandler<P, R> {
	String getType();
	
	Class<?> getParameterClass();
	
	Class<?> getResultClass();
}
