package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controller.UsersLogic;
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
			password = ServletUtils.hashPassword(password);
			String confirmation = ServletUtils.getFieldValue(req, Field.CONFIRMATION);
			confirmation = ServletUtils.hashPassword(confirmation);
			HttpSession session = req.getSession(false);
			
			String firstname = ServletUtils.getFieldValue(req, Field.FIRSTNAME);
			String name = ServletUtils.getFieldValue(req, Field.NAME);
			String email = ServletUtils.getFieldValue(req, Field.EMAIL);
			
			UsersLogic userslogic = new UsersLogic();
			
			userslogic.newUser(username, password, confirmation, firstname, name, email, session);
			
			ServletUtils.sendToClient(resp, ServletUtils.jsonSucess());
			
			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION +": "+e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String myself = ServletUtils.getFieldValue(req, Field.MYSELF);
			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			HttpSession session = req.getSession(false);
			
			JSONObject result = ServletUtils.jsonSucess();
			
			UsersLogic userslogic = new UsersLogic();
			
			if(myself != null) {
				userslogic.getMyself(session, result);
			}
			else if(username != null) {
				userslogic.getUsers(username, session, result);
			}else {
				userslogic.getUsers(result);
			}
			
			ServletUtils.sendToClient(resp, result);
			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION +": "+e.getMessage()));
			e.printStackTrace();
		}
	}
	
	

	
	
}

