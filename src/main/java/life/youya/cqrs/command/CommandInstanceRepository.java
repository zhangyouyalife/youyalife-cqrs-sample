package life.youya.cqrs.command;

import java.util.Optional;

public interface CommandInstanceRepository {
	<P, R> void create(CommandInstance<?, ?> instance) throws DuplicateCommandInstanceException;
	
	void finish(CommandInstance<?, ?> instance);
	
	Optional<CommandInstance<?, ?>> get(String id);
}
