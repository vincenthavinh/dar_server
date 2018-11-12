package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class Send {
	
	private static String apiKey;
	
	public static String getApiKey() throws Exception {
		if(apiKey == null) {
			File catalinaBase = new File( System.getProperty( "catalina.base" ) ).getAbsoluteFile();
			File apiKeyFile = new File( catalinaBase, "webapps/dar_server/WebContent/WEB-INF/APIkey" );
			BufferedReader br = new BufferedReader(new FileReader(apiKeyFile));
		    apiKey = br.readLine();
		    br.close();
		}
		return apiKey;
	}
	
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
	
	public static void apiSearch() {
		
	}
}
