package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.Send;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
	    resp.setCharacterEncoding( "UTF-8" );
	    PrintWriter out = resp.getWriter();
	    
	    Enumeration<String> parameterNames = req.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
	        String param = (String) parameterNames.nextElement();
	        out.println(param + " = [" + req.getParameter(param) + "]<br/>");
	        try {
	        	out.println("path : "+new java.io.File( ".").getCanonicalPath());
				//out.println("apikey: " + Send.getApiKey());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}
