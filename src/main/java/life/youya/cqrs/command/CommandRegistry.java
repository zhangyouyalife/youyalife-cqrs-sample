package life.youya.cqrs.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandRegistry {
	Map<String, Command<Object, Object>> queries = new HashMap<>();
	
	public void addCommand(Command<Object, Object> command) {
		queries.put(command.getType(), command);
	}
	
	public Optional<Command<Object, Object>> getCommandByType(String type) {
		return Optional.ofNullable(queries.get(type));
	}
}
