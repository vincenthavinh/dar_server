package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controller.QuestionsLogic;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

@SuppressWarnings("serial")
public class Questions extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			HttpSession session = req.getSession(false);

			JSONObject result = ServletUtils.jsonSucess();
			
			QuestionsLogic questionslogic = new QuestionsLogic();
			questionslogic.newRandomQuestion(2, session, result);

			ServletUtils.sendToClient(resp, result);

			
		} catch (CustomException e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(e.getMessage()));
		} catch (Exception e) {
			ServletUtils.sendToClient(resp, ServletUtils.jsonFailure(Field.EXCEPTION + ": " + e.getMessage()));
			e.printStackTrace();
		}
	}
}
