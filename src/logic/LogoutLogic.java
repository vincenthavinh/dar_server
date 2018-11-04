package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutLogic extends Logic {
	
	public void invalidateSession(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		
		/*check session existante*/
		if(session == null) {
			errors.put(SESSION_FIELD, "Aucune session en cours.");
		}
		
		if(errors.isEmpty()) {
			session.invalidate();
		}
		
		setResult();
	}

}
