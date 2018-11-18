package controller;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import database.DB;
import database.dao.DAOUser;
import model.User;
import tools.CustomException;
import tools.Field;

public class FriendsLogic extends Logic {
	
	private DAOUser daouser = new DAOUser(DB.get());

	public void addFriend(String friendUsername, HttpSession session) throws CustomException {
		
		/*check session valide*/
		checkSession(session);
		
		User friendUser = daouser.read(friendUsername);
		
		/*check friendUsername*/
		if(friendUser == null) {
			throw new CustomException(Field.USERNAME +":l'utilisateur ["+ friendUsername +"] n'existe pas.");
		}
		
		String username = (String) session.getAttribute(Field.USERNAME);
		
		/*check que l'user ne s'ajoute pas lui-meme*/
		if(friendUsername.equals(username)) {
			throw new CustomException(Field.USERNAME +": ["+ friendUsername +"] est l'username de la session en cours.");
		}
		
		/*check deja amis*/
		if(friendUser.getFriends() != null && friendUser.getFriends().contains(username) == true) {
			throw new CustomException(Field.USERNAME +": ["+ username +"] et ["+ friendUsername +"] sont déjà amis.");
		}
		
		/*creation de l'amitie*/
		daouser.updateAddFriend(username, friendUsername);
		daouser.updateAddFriend(friendUsername, username);
		
		
	}

	public void getFriends(String username, JSONObject result) throws CustomException {
		
		User user = daouser.read(username);
		
		/*check username*/
		if(user ==null) {
			throw new CustomException(Field.USERNAME +":l'utilisateur ["+ username +"] n'existe pas.");
		}
		
		result.put("username", username);
		result.put("friends", user.getFriends());
	}

	public void removeFriend(String friendUsername, HttpSession session) throws CustomException {
		
		/*check session valide*/
		checkSession(session);
		
		String username = (String) session.getAttribute(Field.USERNAME);
		User user = daouser.read(username);
		
		/*check est ami*/
		if(user.getFriends() != null && user.getFriends().contains(friendUsername) == false) {
			throw new CustomException(Field.USERNAME +": ["+ username +"] et ["+ friendUsername +"] ne sont pas amis.");
		}
		
		/*suppression de l'amitie*/
		daouser.updateRemoveFriend(username, friendUsername);
		daouser.updateRemoveFriend(friendUsername, username);
	}
	
}
