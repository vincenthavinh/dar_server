package logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

public abstract class Logic {
	
	protected static final String USERNAME_FIELD = "username";
	protected static final String PASSWORD_FIELD = "password";
	protected static final String CONFIRMATION_FIELD = "confirmation";
	protected static final String SESSION_FIELD = "session";

	protected HttpServletRequest req;
	protected Map<String, String> errors = new HashMap<String, String>();

	protected Logic(HttpServletRequest req) {
		this.req = req;
	}
	
	
	protected String getFieldValue(HttpServletRequest req, String fieldname) {
		String value = req.getParameter(fieldname);

		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value.trim();
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		
		if (errors.isEmpty()) {
			json.put("success", true);
		} else {
			json.put("success", false);
			JSONObject jsonerrors = new JSONObject(errors);
			json.put("errors", jsonerrors);
		}
		
		return json;
	}
	
	protected void isSessionValid() {
		HttpSession session = req.getSession(false);
		if(session == null) {
			errors.put(SESSION_FIELD, "Aucune session en cours.");
		}
	}

}
