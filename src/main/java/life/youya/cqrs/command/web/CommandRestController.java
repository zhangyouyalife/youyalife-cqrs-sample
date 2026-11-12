package life.youya.cqrs.command.web;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import life.youya.cqrs.command.Command;
import life.youya.cqrs.command.CommandInstance;
import life.youya.cqrs.command.CommandRegistry;
import life.youya.cqrs.query.Query;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/command")
public class CommandRestController {
	
	CommandRegistry commandRegistry;
	
	@Autowired
	JsonMapper jsonMapper;
	
	@Autowired
	public ApplicationContext applicationContext;
	
	@PostMapping
	public ResponseEntity<?> query(@RequestBody CommandBody commandMessage) {
		
		String type = commandMessage.getType();
		Optional<Command<Object, Object>> command = commandRegistry.getCommandByType(type);
		if (command.isEmpty()) {
			return ResponseEntity.badRequest().body("Type required"); 
		}
		
		String commandId = commandMessage.getId();
		if (StringUtils.isBlank(commandId)) {
			return ResponseEntity.badRequest().body("Id required");
		}
						
		Object parameter = jsonMapper.readerFor(command.get().getParameterClass()).readValue(commandMessage.getParameter());
		
		CommandInstance<Object, Object> instance = new CommandInstance<>();
		instance.setCommand(command.get());
		instance.setId(commandId);
		instance.setParameter(parameter);
		
		command.get().handle(instance);
		
		commandMessage.setResult(instance.getResult());
		commandMessage.setStatus(instance.getStatus());
		
		return ResponseEntity.ok(commandMessage);
	}
	
	String generateQueryId() {
		return UUID.randomUUID().toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void init() {
		
		commandRegistry = new CommandRegistry();
		
		Map<String, Command> commands = applicationContext.getBeansOfType(Command.class);
		
		for (Command<Object, Object> q : commands.values()) {
			commandRegistry.addCommand(q);
		}
	}
}
