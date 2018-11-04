package views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import logic.Logic;

public class LogicView {

	public static void sendJSONResultErrors(HttpServletResponse resp, Logic logic) {
		JSONObject json = new JSONObject();
		json.put("result", logic.getResult());
		
		JSONObject jsonerrors = new JSONObject(logic.getErrors());
		json.put("errors", jsonerrors);
	
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
      
        PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
        out.print(json);
        out.flush();   
	}
	
}
