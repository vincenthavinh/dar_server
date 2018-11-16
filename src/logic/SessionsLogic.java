package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;
import tools.CustomException;
import tools.Field;

public class SessionsLogic extends Logic {
	
	private DAOUser daouser = new DAOUser(DB.get());
	private static int maxInactiveTime = 300;
	
	public void connectUser(String username, String password, HttpServletRequest req) throws CustomException {
		
		/* check pas de Session dans la HttpServletRequest */
		checkNoSession(req.getSession(false));
		
		//on recupere l'utilisateur dans la bdd
		User user  = daouser.read(username);
		
		/*check username*/
		if(user ==null) {
			throw new CustomException(Field.USERNAME +":l'utilisateur ["+ username + "] n'existe pas.");
		}
		
		/*check password*/
		if(password == null || !password.equals(user.getPassword())) {
			throw new CustomException(Field.PASSWORD+ ": erroné.");
		}
		
		/*creation de la session*/
		HttpSession session = req.getSession(true);
		session.setAttribute(Field.USERNAME, username);
		session.setMaxInactiveInterval(maxInactiveTime);
	}

	public void invalidateSession(HttpServletRequest req) throws CustomException {
		HttpSession session = req.getSession(false);
		
		/*check session existante*/
		checkSession(session);
		
		/*suppression de la session*/
		session.invalidate();
	}
}
