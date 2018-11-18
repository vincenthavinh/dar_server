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
	
	public void getMyself(HttpSession session, JSONObject result) throws CustomException {
		
		/*check session valide*/
		checkSession(session);
		
		User user = daouser.read((String) session.getAttribute(Field.USERNAME));
		
		//toutes les infos (publiques et privees) sauf le mdp.
		JSONObject userjson = new JSONObject(user);
		userjson.remove("password");
		
		result.put("user", userjson);
		
	}
	
	public void getUsers(JSONObject result) {
		List<User> users = daouser.readUsers();
		
		JSONArray usersjson = new JSONArray();
		
		for(User user : users) {
			JSONObject userjson = new JSONObject();
			
			//infos publiques
			userjson.put("username", user.getUsername());
			userjson.put("score", user.getScore());
			usersjson.put(userjson);
		}
	
		result.put("users", usersjson);
	}
	
	public void getUsers(String search, HttpSession session, JSONObject result) throws CustomException {
		
		/* Recherche des users dont le username contient search */
		List<User> users = daouser.readUsers(search);
		if(users.size() == 0) {
			throw new CustomException(Field.USERNAME +": aucun utilisateur ne correspond à la recherche ["+ search +"].");
		}
		
		JSONArray usersjson = new JSONArray();
		
		for(User user : users) {
			JSONObject userjson = new JSONObject();
			userjson.put("username", user.getUsername());
			userjson.put("score", user.getScore());
			
			usersjson.put(userjson);
		}
		
		/* ajout des infos dans le json resultat */
		result.put("users", usersjson);
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
