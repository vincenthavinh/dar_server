package tools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;

public class ServletUtils {

	public static String getFieldValue(HttpServletRequest req, String fieldname) {
		String value = req.getParameter(fieldname);

		if (value == null || value.trim().length() == 0) {
			return null;
		} else {
			return value.trim();
		}
	}

	public static JSONObject jsonSucess() {
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}

	public static JSONObject jsonFailure(String error) {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("error", error);
		return json;
	}

	public static void sendToClient(HttpServletResponse resp, JSONObject json) {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(StringEscapeUtils.unescapeJson(json.toJSONString()));
		out.flush();
	}

}
