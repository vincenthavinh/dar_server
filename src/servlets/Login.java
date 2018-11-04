package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.LoginLogic;
import views.LogicView;

public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LoginLogic loginlogic = new LoginLogic();
		loginlogic.connectUser(req);
        
        LogicView.sendJSONResultErrors(resp, loginlogic);
	}

	

	
}
