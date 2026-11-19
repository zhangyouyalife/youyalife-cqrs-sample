package life.youya.cqrs.command;

public class DuplicateCommandInstanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CommandInstance<?, ?> instance;
	
	public DuplicateCommandInstanceException(CommandInstance<?, ?> instance) {
		this.instance = instance;
	}
	
	public CommandInstance<?, ?> getCommandInstance() {
		return instance;
	}

}
