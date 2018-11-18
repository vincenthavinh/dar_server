package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controller.FriendsLogic;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

@SuppressWarnings("serial")
public class Friends extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			HttpSession session = req.getSession(false);
			
			FriendsLogic friendslogic = new FriendsLogic();
			friendslogic.addFriend(username, session);
			
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
			
			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			
			JSONObject result = new JSONObject();
			
			FriendsLogic friendslogic = new FriendsLogic();
			friendslogic.getFriends(username, result);
			
			ServletUtils.sendToClient(resp, result);
			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION +": "+e.getMessage()));
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			HttpSession session = req.getSession(false);
			
			FriendsLogic friendslogic = new FriendsLogic();
			friendslogic.removeFriend(username, session);
			
			ServletUtils.sendToClient(resp, ServletUtils.jsonSucess());
			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION +": "+e.getMessage()));
			e.printStackTrace();
		}
	}
}
