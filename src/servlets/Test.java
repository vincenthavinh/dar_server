package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Test extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
	    resp.setCharacterEncoding( "UTF-8" );
	    PrintWriter out = resp.getWriter();
	    
	    Enumeration<String> parameterNames = req.getParameterNames();
	    while (parameterNames.hasMoreElements()) {
	        String param = (String) parameterNames.nextElement();
	        out.println(param + " = [" + req.getParameter(param) + "]<br/><br/>");
	    }
	    
	    //System.out.println("apikey : " + CDiscountUtils.getApiKey());
	    
	    
	    /*-----------------*/
//	    QuestionsLogic questionslogic = new QuestionsLogic(req);
//	    questionslogic.newRandomQuestion(2);
//	    
//	    out.println("<br/><br/>");
//	    ServletUtils.sendToClient(resp, questionslogic.toJSON());
	}
}
