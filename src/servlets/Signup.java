package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.SignupLogic;
import views.LogicView;

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
		signuplogic.newUser(req);

		LogicView.sendJSONResultErrors(resp, signuplogic);
    	//System.out.println("FIN SIGNUP-----------------------------------------");
	}

	
	
}
