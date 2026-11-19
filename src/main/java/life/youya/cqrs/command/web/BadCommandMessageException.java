package life.youya.cqrs.command.web;

public class BadCommandMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CommandBody commandBody;
		
	public BadCommandMessageException(String message, CommandBody commandMessage) {
		super(message);
		
		this.commandBody = commandMessage;
	}

	public CommandBody getCommandMessage() {
		return commandBody;
	}

	public void setCommandMessage(CommandBody commandMessage) {
		this.commandBody = commandMessage;
	}

}
