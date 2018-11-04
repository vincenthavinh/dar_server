package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.LoginLogic;
import logic.LogoutLogic;
import views.LogicView;

public class Logout extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LogoutLogic logoutlogic = new LogoutLogic();
		logoutlogic.invalidateSession(req);;
        
        LogicView.sendJSONResultErrors(resp, logoutlogic);
	}

}
