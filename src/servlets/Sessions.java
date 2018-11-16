package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import logic.SessionsLogic;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

@SuppressWarnings("serial")
public class Sessions extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			String username = ServletUtils.getFieldValue(req, Field.USERNAME);
			String password = ServletUtils.getFieldValue(req, Field.PASSWORD);

			SessionsLogic sessionslogic = new SessionsLogic();
			sessionslogic.connectUser(username, password, req);

			ServletUtils.sendToClient(resp, ServletUtils.jsonSucess());

		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION + ": " + e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			SessionsLogic sessionslogic = new SessionsLogic();

			sessionslogic.invalidateSession(req);

			ServletUtils.sendToClient(resp, ServletUtils.jsonSucess());

			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION + ": " + e.getMessage()));
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			HttpSession session = req.getSession(false);
			
			JSONObject result = ServletUtils.jsonSucess();
			
			SessionsLogic sessionslogic = new SessionsLogic();
			sessionslogic.getInformations(session, result);
			
			ServletUtils.sendToClient(resp, result);

			
//		} catch (CustomException e) {
//			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION + ": " + e.getMessage()));
			e.printStackTrace();
		}
	}
}
