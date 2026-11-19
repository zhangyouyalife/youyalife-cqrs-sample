package life.youya.cqrs.command;

public class CommandInstance<P, R> {
	Command<P, R> command;
	String id;
	CommandStatus status;
	P parameter;
	R result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Command<P, R> getCommand() {
		return command;
	}
	public void setCommand(Command<P, R> command) {
		this.command = command;
	}
	public CommandStatus getStatus() {
		return status;
	}
	public void setStatus(CommandStatus status) {
		this.status = status;
	}
	public P getParameter() {
		return parameter;
	}
	public void setParameter(P parameter) {
		this.parameter = parameter;
	}
	public R getResult() {
		return result;
	}
	public void setResult(R result) {
		this.result = result;
	}
}
