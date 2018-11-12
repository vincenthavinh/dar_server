package logic;

import javax.servlet.http.HttpServletRequest;

import beans.User;
import dao.DB;
import dao.objects.DAOUser;

public class UsersLogic extends Logic {
	
	private DAOUser daouser;
	
	public UsersLogic(HttpServletRequest req) {
		super(req);
		daouser = new DAOUser(DB.get());
	}

	public User newUser() {
		String username = getFieldValue(req, USERNAME_FIELD);
		String password = getFieldValue(req, PASSWORD_FIELD);
		String confirmation = getFieldValue(req, CONFIRMATION_FIELD);

		User user = new User();

		/* Username */
		checkUsername(username);
		user.setUsername(username);

		/* password */
		checkPasswordConfirmation(password, confirmation);
		user.setPassword(password);

		/*ajout de l'utilisateur a la bdd*/
		if(errors.isEmpty()) {
			try {
				daouser.create(user);
			} catch (Exception e) {
				errors.put("mongodb", "erreur survenue lors de l'insertion dans la bdd");
			}
		}

		return user;
	}

	private void checkUsername(String username) {
		if (username == null || username.length() < 3) {
			errors.put(USERNAME_FIELD, "Le nom d'utilisateur doit contenir au moins 3 caractères.");
			return;
		}
		if (daouser.read(username) != null) {
			errors.put(USERNAME_FIELD, "Le nom d'utilisateur est déjà utilisé.");
			return;
		}
	}

	private void checkPasswordConfirmation(String password, String confirmation) {
		if (password == null || confirmation == null) {
			errors.put(PASSWORD_FIELD, "Merci de saisir et confirmer votre mot de passe.");
			return;
		}
		if (!password.equals(confirmation)) {
			errors.put(PASSWORD_FIELD, "Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			return;
		}
		if (password.length() < 6) {
			errors.put(PASSWORD_FIELD, "Les mots de passe doivent contenir au moins 6 caractères.");
			return;
		}
	}

}
