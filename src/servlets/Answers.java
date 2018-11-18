package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controller.AnswersLogic;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

@SuppressWarnings("serial")
public class Answers extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {

			String answer = req.getParameter(Field.ANSWER);
			HttpSession session = req.getSession(false);
			
			JSONObject result = ServletUtils.jsonSucess();

			AnswersLogic answerslogic = new AnswersLogic();
			answerslogic.processAnswer(answer, session, result);

			ServletUtils.sendToClient(resp, result);

			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION + ": " + e.getMessage()));
			e.printStackTrace();
		}
	}
}
