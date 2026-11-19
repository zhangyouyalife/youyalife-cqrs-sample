package life.youya.cqrs.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tools.jackson.databind.json.JsonMapper;

@Component
public class JsonUtility {
	@Autowired
	JsonMapper jsonMapper;
	
	String toJsonText(Object obj) {
		return jsonMapper.writeValueAsString(obj);
	}
	
	<T> T fromJsonText(String jsonText, Class<T> clazz) {
		return jsonMapper.readerFor(clazz).readValue(jsonText);
	}
}
