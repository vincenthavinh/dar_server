package tools;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;

public class ServletUtils {
	
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
