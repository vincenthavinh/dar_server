package logic;

import javax.servlet.http.HttpSession;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;
import tools.CustomException;
import tools.Field;

public class UsersLogic extends Logic {
	
	private DAOUser daouser = new DAOUser(DB.get());
	
	public UsersLogic() {
		daouser = new DAOUser(DB.get());
	}

	public User newUser(String username, String password, String confirmation, HttpSession session) throws CustomException {
		User user = new User();

		/* Session */
		checkNoSession(session);
		
		/* Username */
		checkUsername(username);
		user.setUsername(username);

		/* password */
		checkPasswordConfirmation(password, confirmation);
		user.setPassword(password);

		/*ajout de l'utilisateur a la bdd*/
		daouser.create(user);
		
		return user;
	}

	private void checkUsername(String username) throws CustomException {
		if (username == null || username.length() < 3) {
			throw new CustomException(Field.USERNAME +": trop court (minimum 3 caractères).");
		}
		if (daouser.read(username) != null) {
			throw new CustomException(Field.USERNAME +": déjà utilisé."); 
		}
	}

	private void checkPasswordConfirmation(String password, String confirmation) throws CustomException {
		if (password == null || password.length() < 6) {
			throw new CustomException(Field.PASSWORD +": trop court (minimum 6 caractères).");
		}
		
		if (confirmation == null || !password.equals(confirmation)) {
			throw new CustomException(Field.CONFIRMATION +": différent du mot de passe.");
		}
	}

}
