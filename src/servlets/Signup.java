package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import logic.SignupLogic;

public class Signup extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*System.out.println("DEBUT SIGNUP-----------------------------------------");
		Enumeration<String> parameterNames = req.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
	        String param = (String) parameterNames.nextElement();
	        System.out.println(param + " = [" + req.getParameter(param) + "]");
	    }*/
		
		SignupLogic signuplogic = new SignupLogic();
		User user = signuplogic.newUser(req);

		
		String jsonstring = signuplogic.getJSONResultErrors().toString();
		 
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonstring);
        out.flush();   
    	//System.out.println("FIN SIGNUP-----------------------------------------");
	}

	
	
}
