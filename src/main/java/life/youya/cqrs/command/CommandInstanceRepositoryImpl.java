package life.youya.cqrs.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CommandInstanceRepositoryImpl implements CommandInstanceRepository {

	@Autowired
	JdbcOperations jdbcOperations;
	
	@Autowired
	JsonUtility jsonUtility;
	
	@Autowired
	CommandRegistry commandRegistry;
	
	@Override
	public <P, R> void create(CommandInstance<?, ?> instance) throws DuplicateCommandInstanceException {
		String sql = """
				INSERT INTO command_instance(id, type, status, parameter, create_at)
				VALUES (?, ?, ?, ?, ?);
				""";
		
		try {
			jdbcOperations.update(sql,
					instance.getId(),
					instance.getCommand().getType(),
					instance.getStatus().toString(),
					jsonUtility.toJsonText(instance.getParameter()),
					LocalDateTime.now());
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new DuplicateCommandInstanceException(get(instance.getId()).get());
		}
	}

	@Override
	public Optional<CommandInstance<?, ?>> get(String id) {
		String sql = """
				SELECT id, type, status, parameter, result, create_at, finish_at
				FROM command_instance
				WHERE id = ?;
				""";
		
		return Optional.ofNullable(
				jdbcOperations.queryForObject(sql, new RowMapper<CommandInstance<?, ?>>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public CommandInstance<?, ?> mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommandInstance<Object, Object> inst = new CommandInstance<>();
				inst.setId(id);
				
				Command<?, ?> command = commandRegistry.getCommandByType(rs.getString("type"));
				inst.setCommand((Command) command);
				
				String p = rs.getString("parameter");
				if (p != null) {
					inst.setParameter(jsonUtility.fromJsonText(p, command.getParameterClass()));
				}
				
				String r = rs.getString("result");
				if (r != null) {
					inst.setParameter(jsonUtility.fromJsonText(r, command.getParameterClass()));
				}
				
				inst.setStatus(CommandStatus.valueOf(rs.getString("status")));
				
				return inst;
			}
			
		}, id));
	}

	@Override
	public void finish(CommandInstance<?, ?> instance) {
		String sql = """
				UPDATE command_instance SET status = ?, result = ?, finish_at = ?
				WHERE id = ?;
				""";
		
		jdbcOperations.update(sql,
				instance.getStatus().toString(),
				jsonUtility.toJsonText(instance.getResult()),
				LocalDateTime.now(),
				instance.getId());
	}

}
