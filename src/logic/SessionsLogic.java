package logic;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;
import tools.CustomException;
import tools.Field;
import tools.ServletUtils;

public class SessionsLogic extends Logic {
	
	private DAOUser daouser = new DAOUser(DB.get());
	private static int maxInactiveTime = 300;
	
	public JSONObject toJSON(HttpSession session) {
		JSONObject json = ServletUtils.jsonSucess();
		
		JSONObject sessionjson;
		if (session == null) {
			json.put("session", JSONObject.NULL);
		}else {
			sessionjson = new JSONObject();
			
			/*ajout des champs custom de la session*/
			Enumeration<String> e = (Enumeration<String>) (session.getAttributeNames());
			while (e.hasMoreElements()) {
				String key;
				if ((key = e.nextElement()) != null) {
					sessionjson.put(key, session.getAttribute(key));
				}
			}
			
			/*ajout des proprietes de la session*/
			sessionjson.put("id", session.getId());
			sessionjson.put("creationTime", new Date(session.getCreationTime()).toString());
			sessionjson.put("lastAccessedTime", new Date(session.getLastAccessedTime()).toString());
			sessionjson.put("maxInactiveInterval", session.getMaxInactiveInterval());
			sessionjson.put("isNew", session.isNew());
			
			/*ajout de la session au json*/
			json.put("session", sessionjson);
		}
		
		return json;
	}
	
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
