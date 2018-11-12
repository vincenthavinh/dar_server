package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class QuestionsLogic extends Logic {
	
	private static String[] keywords = {"tablette"};

	public QuestionsLogic(HttpServletRequest req) {
		super(req);
		// TODO Auto-generated constructor stub
	}
	
	public void newRandomQuestion() {
		
		/*check session existante*/
		HttpSession session = req.getSession(false);
		if(session == null) {
			errors.put(SESSION_FIELD, "Aucune session en cours.");
		}
		
		/*nouveau random product*/
		
	}
}
