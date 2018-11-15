package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;

public class SessionsLogic extends Logic {
	
	private DAOUser daouser;
	private static int maxInactiveTime = 300;
	
	public SessionsLogic(HttpServletRequest req) {
		super(req);
		daouser = new DAOUser(DB.get());
	}
	
	public void connectUser() {
		String username = getFieldValue(req, USERNAME_FIELD);
		String password = getFieldValue(req, PASSWORD_FIELD);
		
		/*check session deja existante*/
		if(req.getSession(false) != null) {
			errors.put(SESSION_FIELD, "Une session est déjà en cours.");
		}
		
		User user  = daouser.read(username);
		
		/*check username*/
		if(user ==null) {
			errors.put(USERNAME_FIELD, "l'utilisateur ["+ username + "] n'existe pas.");
		}
		
		/*check password*/
		else if(password == null || !password.equals(user.getPassword())) {
			errors.put(PASSWORD_FIELD, "mot de passe erroné.");
		}
		
		/*creation de la session*/
		HttpSession session = null;
		if(errors.isEmpty()) {
			session = req.getSession();
			session.setAttribute(USERNAME_FIELD, username);
			session.setMaxInactiveInterval(maxInactiveTime);
		}
	}

	public void invalidateSession() {
		HttpSession session = req.getSession(false);
		
		/*check session existante*/
		if(session == null) {
			errors.put(SESSION_FIELD, "Aucune session en cours.");
		}
		
		if(errors.isEmpty()) {
			session.invalidate();
		}
	}
}
