package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.AnswersLogic;
import logic.QuestionsLogic;
import tools.ServletUtils;

public class Answers extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("answer="+ req.getParameter("answer"));
	    AnswersLogic answerslogic = new AnswersLogic(req);
	    answerslogic.handleAnswer();
	    
	    ServletUtils.sendToClient(resp, answerslogic.toJSON());
	}
}
