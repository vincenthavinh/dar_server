package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
