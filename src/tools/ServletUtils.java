package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class ServletUtils {
	
	public static void answerToClient(HttpServletResponse resp, JSONObject json) {
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
