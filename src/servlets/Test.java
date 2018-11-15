package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Question;
import logic.QuestionsLogic;
import tools.CDiscountUtils;
import tools.ServletUtils;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
	    QuestionsLogic questionslogic = new QuestionsLogic(req);
	    questionslogic.newRandomQuestion(2);
	    
	    out.println("<br/><br/>");

	    ServletUtils.answerToClient(resp, questionslogic.toJSON());
	   
	    //out.println("PRICE?: "+ products.get(rand.nextInt(products.size())).getSalePrice() +"<br/>");
	    
//	    for(Product prod : products ) {
//	    	out.println("Pid: "+ prod.getPid() +", SalePrice: "+ prod.getSalePrice() +"<br/>");
//	    }
	    
//	    out.println("<br/><br/>");
//	    for(Product prod : products) {
//	    	out.println("Product:<br/>"
//		    		+ "Pid: "+ prod.getPid() +"<br/>"
//		    		+ "Name: "+ prod.getName() +"<br/>"
//		    		+ "ImageUrl: "+ prod.getImagesUrls() +"<br/>"
//		    		+ "SalePrice: "+ prod.getSalePrice() +"<br/>"
//		    		+ "ProductUrl: "+ prod.getProductUrl() +"<br/>");
//		    for(String imgUrl : prod.getImagesUrls()) {
//		    	out.println(" <img src=\" "+ imgUrl + "\" alt=\"MainImage\" > ");
//		    }
//	    }
	}
}
