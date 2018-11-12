package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.UsersLogic;
import tools.Send;

public class Users extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		UsersLogic userslogic = new UsersLogic(req);
		userslogic.newUser();

		Send.answerToClient(resp, userslogic.toJSON());
	}

	
	
}

