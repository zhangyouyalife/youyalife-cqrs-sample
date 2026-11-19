package life.youya.cqrs.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CommandRegistry {
	Map<String, Command<?, ?>> queries = new HashMap<>();
	
	public void addCommand(Command<?, ?> command) {
		queries.put(command.getType(), command);
	}
	
	public Command<?, ?> getCommandByType(String type) {
		return queries.get(type);
	}
}
