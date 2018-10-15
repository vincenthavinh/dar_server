package logic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class Logic {
	
	protected String result;
	protected Map<String, String> errors = new HashMap<String, String>();

	protected String getFieldValue(HttpServletRequest req, String fieldname) {
		String value = req.getParameter(fieldname);

		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value.trim();
		}
	}
	
	protected void setError(String field, String message) {
		errors.put(field, message);
	}
	
	public JSONObject getJSONResultErrors() {
		JSONObject json = new JSONObject();
		json.put("result", result);
		
		JSONObject jsonerrors = new JSONObject(errors);
		json.put("errors", jsonerrors);
	
		return json;
	}

}
