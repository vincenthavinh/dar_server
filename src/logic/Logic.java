package logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import tools.CustomException;
import tools.Field;

public abstract class Logic {

	protected Map<String, String> errors = new HashMap<String, String>();

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
	
	protected void checkNoSession(HttpSession session) throws CustomException {
		if(session != null) {
			throw new CustomException(Field.SESSION +": déjà connecté.");
		}
	}
	
	protected void checkSession(HttpSession session) throws CustomException {
		if(session == null) {
			throw new CustomException(Field.SESSION +": non connecté.");
		}
	}
}
