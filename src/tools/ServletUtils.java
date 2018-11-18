package tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ServletUtils {

	public static String hashPassword(String text) throws NoSuchAlgorithmException {
		String encoded = null;
		
		if(text != null) {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
			encoded = Base64.getEncoder().encodeToString(hash);
		}
		return encoded;
	}
	
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
		out.println(json.toString(4));
		out.flush();
	}

}
