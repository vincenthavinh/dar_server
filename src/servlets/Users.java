package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.UsersLogic;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

@SuppressWarnings("serial")
public class Users extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			String password = ServletUtils.getFieldValue(req, Field.PASSWORD);
			HttpSession session = req.getSession(false);
			String confirmation = ServletUtils.getFieldValue(req, Field.CONFIRMATION);
			
			UsersLogic userslogic = new UsersLogic();
			
			userslogic.newUser(username, password, confirmation, session);
			
			ServletUtils.sendToClient(resp, userslogic.toJSON());
			
			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION +": "+e.getMessage()));
			e.printStackTrace();
		}
	}

	
	
}

