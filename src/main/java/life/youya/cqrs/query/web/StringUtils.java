package life.youya.cqrs.query.web;

public class StringUtils {
	public static boolean isBlank(String text) {
		return "".equals(text) || text == null;
	}
}
