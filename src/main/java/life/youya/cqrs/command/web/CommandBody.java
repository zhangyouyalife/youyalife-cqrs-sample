package life.youya.cqrs.command.web;

import life.youya.cqrs.command.CommandStatus;
import tools.jackson.databind.JsonNode;

public class CommandBody {
	
	private String type;
	
	private String id;
	
	private CommandStatus status;
	
	private JsonNode parameter;
	
	private Object result;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public CommandStatus getStatus() {
		return status;
	}
	public void setStatus(CommandStatus status) {
		this.status = status;
	}
	
	public JsonNode getParameter() {
		return parameter;
	}
	public void setParameter(JsonNode parameter) {
		this.parameter = parameter;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

}
