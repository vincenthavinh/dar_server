package logic;

import javax.servlet.http.HttpServletRequest;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;

public class SignupLogic extends Logic {

	private static final String USERNAME_FIELD = "username";
	private static final String PASSWORD_FIELD = "password";
	private static final String CONFIRMATION_FIELD = "confirmation";
	
	private DAOUser daouser;
	
	public SignupLogic() {
		daouser = new DAOUser(DB.get());
	}

	public User newUser(HttpServletRequest req) {
		String username = getFieldValue(req, USERNAME_FIELD);
		String password = getFieldValue(req, PASSWORD_FIELD);
		String confirmation = getFieldValue(req, CONFIRMATION_FIELD);

		User user = new User();

		/* Username */
		checkUsername(username);
		user.setUsername(username);

		/* password */
		checkPassword(password, confirmation);
		user.setPassword(password);

		/*ajout de l'utilisateur a la bdd*/
		if(errors.isEmpty()) {
			try {
				daouser.create(user);
			} catch (Exception e) {
				setError("mongodb", "erreur survenue lors de l'insertion dans la bdd");
			}
		}
		
		/*confirmation de l'inscription*/
		if (errors.isEmpty()) {
			result = "Inscription effectuée.";
		} else {
			result = "Échec de l'inscription.";
		}

		return user;
	}

	private void checkUsername(String username) {
		if (username != null && username.length() < 3) {
			setError(USERNAME_FIELD, "Le nom d'utilisateur doit contenir au moins 3 caractères.");
			return;
		}
		if (daouser.read(username) != null) {
			setError(USERNAME_FIELD, "Le nom d'utilisateur est déjà utilisé.");
			return;
		}
	}

	private void checkPassword(String password, String confirmation) {
		if (password == null || confirmation == null) {
			setError(PASSWORD_FIELD, "Merci de saisir et confirmer votre mot de passe.");
			return;
		}
		if (!password.equals(confirmation)) {
			setError(PASSWORD_FIELD, "Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			return;
		}
		if (password.length() < 6) {
			setError(PASSWORD_FIELD, "Les mots de passe doivent contenir au moins 6 caractères.");
			return;
		}
	}

}
