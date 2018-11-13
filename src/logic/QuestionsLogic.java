package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class QuestionsLogic extends Logic {
	
	private static String[] keywords = {"tablette", "smartphone", "ordinateur", "cuisine", "table", "siege"};

	public QuestionsLogic(HttpServletRequest req) {
		super(req);
		// TODO Auto-generated constructor stub
	}
	
	public void newRandomQuestion() {
		
		/*check session valide*/
		isSessionValid();
		
		/*nouveau random product*/
		
	}
}
