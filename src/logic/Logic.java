package logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public abstract class Logic {
	
	protected static final String USERNAME_FIELD = "username";
	protected static final String PASSWORD_FIELD = "password";
	protected static final String CONFIRMATION_FIELD = "confirmation";
	protected static final String SESSION_FIELD = "session";

	protected boolean result;
	protected Map<String, String> errors = new HashMap<String, String>();

	protected String getFieldValue(HttpServletRequest req, String fieldname) {
		String value = req.getParameter(fieldname);

		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value.trim();
		}
	}

	public void setResult() {
		if (errors.isEmpty()) {
			result = true;
		} else {
			result = false;
		}
	}

	public boolean getResult() {
		return result;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
