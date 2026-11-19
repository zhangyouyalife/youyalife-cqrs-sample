package life.youya.cqrs.command;

public interface CommandHandler<P, R> {
	void handle(CommandInstance<P, R> instance) throws Exception;
}
