package servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.objects.DAOQuestion;
import tools.CDiscountUtils;

public class StartRoutine implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		/* Au lancement de la web application : */
		
		/* Initialisation de l'attribut statique apiKey de CDiscountUtils */
		ServletContext ctx = sce.getServletContext();
    	String filePath = ctx.getRealPath("/WEB-INF/APIkey");
    	try {
			CDiscountUtils.setApiKey(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
