package logic;

import javax.servlet.http.HttpSession;

import tools.CustomException;
import tools.Field;

public abstract class Logic {
	
	protected void checkNoSession(HttpSession session) throws CustomException {
		if(session != null) {
			throw new CustomException(Field.SESSION +": déjà connecté.");
		}
	}
	
	protected void checkSession(HttpSession session) throws CustomException {
		if(session == null) {
			throw new CustomException(Field.SESSION +": non connecté.");
		}
	}
}
