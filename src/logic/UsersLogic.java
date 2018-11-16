package logic;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public void getTop10Users(JSONObject result) {
		List<User> users = daouser.readTop10Users();
		
		JSONArray usersjson = new JSONArray();
		
		for(User user : users) {
			JSONObject userjson = new JSONObject();
			userjson.put("username", user.getUsername());
			userjson.put("score", user.getScore());
			usersjson.put(userjson);
		}
	
		result.put("top10", usersjson);
	}
	
	public void getUser(String username, HttpSession session, JSONObject result) throws CustomException {
		
		/* lecture de l'user associe a l'username demande*/
		User user = daouser.read(username);
		if(user == null) {
			throw new CustomException(Field.USERNAME +": l'utilisateur ["+ username +"] n'existe pas.");
		}
		JSONObject userjson;
		
		
		/* si session connectee et si request.username = session.username 
		 * on renvoie toutes les infos (sauf mdp). */
		if(session != null && username.equals((String) session.getAttribute("username"))) {
			userjson = new JSONObject(user);
			userjson.remove("password");
		
		/*sinon (pas de session connectee ou demande d'infos sur un autre user que soi)
		 * on ne renvoie que les infos publiques. */
		}else {
			userjson = new JSONObject();
			userjson.put("username", user.getUsername());
			userjson.put("score", user.getScore());
		}
		
		/* ajout des infos dans le json resultat */
		result.put("user", userjson);
	}
	
	public User newUser(String username, String password, String confirmation, String firstname, String name, String email, HttpSession session) throws CustomException {
		User user = new User();

		/* Session */
		checkNoSession(session);
		
		/* Username */
		checkUsername(username);
		user.setUsername(username);

		/* password */
		checkPasswordConfirmation(password, confirmation);
		user.setPassword(password);
		
		/* email (optionnel) */
		checkEmail(email);
		user.setEmail(email);
		
		/* firstname (optionnel) */
		checkFirstname(firstname);
		user.setFirstname(firstname);
		
		/* name (optionnel) */
		checkName(name);
		user.setName(name);

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
	
	/*LE VRAI CHECK serait d'envoyer un email de validation a l'adresse, les regex sont 
	 * trop permissives.*/
	private void checkEmail(String email) throws CustomException {
		if(email != null) {
			if(email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) == false) {
				throw new CustomException(Field.EMAIL +": mal formé." );
			}
		}
	}
	
	private void checkName(String name) throws CustomException {
		if(name != null) {
			if(name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*") == false) {
				throw new CustomException(Field.NAME +": mal formé." );
			}
		}
	}
	
	private void checkFirstname(String firstname) throws CustomException {
		if(firstname != null) {
			if(firstname.matches("[a-zA-z]+([ '-][a-zA-Z]+)*") == false) {
				throw new CustomException(Field.FIRSTNAME +": mal formé." );
			}
		}
	}
}
