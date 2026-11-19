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
import life.youya.cqrs.command.CommandInstanceRepository;
import life.youya.cqrs.command.CommandRegistry;
import life.youya.cqrs.command.CommandStatus;
import life.youya.cqrs.command.DuplicateCommandInstanceException;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/command")
public class CommandRestController {
	
	@Autowired
	CommandRegistry commandRegistry;
	
	@Autowired
	JsonMapper jsonMapper;
	
	@Autowired
	public ApplicationContext applicationContext;
	
	@Autowired
	CommandInstanceRepository commandInstanceRepository;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping
	public ResponseEntity<?> query(@RequestBody CommandBody commandMessage) {
	
		Command<?, ?> command;

		CommandInstance<?, ?> instance;
		
		try {
			
			command = getCommandOfMessage(commandMessage);
			
			instance = createCommandInstance(commandMessage, command);	
			
		} catch (BadCommandMessageException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
			
		} catch (DuplicateCommandInstanceException e) {
			CommandInstance<?, ?> ins =  e.getCommandInstance();
			commandMessage.setResult(ins.getResult());
			commandMessage.setStatus(ins.getStatus());
			return ResponseEntity.ok(commandMessage);
		}
		

		try {
						
			command.handle((CommandInstance) instance);
			
		} catch (Throwable e) {
			instance.setResult(null);
			instance.setStatus(CommandStatus.EXCEPTION);
		}
		
		finishCommandInstance(instance);
		
		commandMessage.setResult(instance.getResult());
		commandMessage.setStatus(instance.getStatus());
		
		return ResponseEntity.ok(commandMessage);
		
	}
	
	String generateQueryId() {
		return UUID.randomUUID().toString();
	}
	
	Command<?, ?> getCommandOfMessage(CommandBody commandMessage) throws BadCommandMessageException {
		String type = commandMessage.getType();
		Command<?, ?> command = commandRegistry.getCommandByType(type);
		
		if (command == null) {
			throw new BadCommandMessageException("Type required", commandMessage);
		}
		return command;
	}
	
	<P, R> CommandInstance<P, R> createCommandInstance(CommandBody commandMessage, Command<P, R> command)
			throws BadCommandMessageException, DuplicateCommandInstanceException {
		
		String commandId = commandMessage.getId();
		if (StringUtils.isBlank(commandId)) {
			throw new BadCommandMessageException("Id required", commandMessage);
		}
		
		CommandInstance<P, R> instance = new CommandInstance<>();
		instance.setCommand(command);
		instance.setId(commandMessage.getId());
		
		P parameter = jsonMapper.readerFor(command.getParameterClass()).readValue(commandMessage.getParameter());
		instance.setParameter(parameter);

		instance.setStatus(CommandStatus.HANDLING);
		
		commandInstanceRepository.create(instance);
		
		return instance;
	}
	
	void finishCommandInstance(CommandInstance<?, ?> instance) {
		commandInstanceRepository.finish(instance);
	}
	 
	
	@PostConstruct
	public void init() {
				
		@SuppressWarnings("rawtypes")
		Map<String, Command> commands = applicationContext.getBeansOfType(Command.class);
		
		for (Command<?, ?> q : commands.values()) {
			commandRegistry.addCommand(q);
		}
	}
}
