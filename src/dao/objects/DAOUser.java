package dao.objects;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import beans.User;
import tools.CustomException;
import tools.Field;


public class DAOUser {

	private  MongoCollection<User> coll;
	
	public DAOUser(MongoDatabase md) {
		coll = md.getCollection("Users", User.class);
		coll.createIndex(Indexes.ascending("username"), new IndexOptions().unique(true));
	}
	
	/**---------------------------------CREATE---------------------------------**/
	public User create(User u) {
		this.coll.insertOne(u);
		
		return u;
	}
	
	/**---------------------------------READ---------------------------------**/
	public User read(String username) throws CustomException {
		User user = this.coll.find(eq("username", username)).first();
		return user;
	}
	
	public List<User> readUsers(String username) {
		String regex = "(?i:.*"+ username + ".*)";
		MongoCursor<User> usersCursor = this.coll.find(regex("username", regex))
				.sort(Sorts.ascending("username"))
				.iterator();
		
		ArrayList<User> users = new ArrayList<User>();
		while(usersCursor.hasNext()) {
			users.add(usersCursor.next());
		}
		
		return users;
	}

	public List<User> readUsers() {
		MongoCursor<User> usersCursor = this.coll.find()
				.sort(descending("score"))
				.iterator();
		
		ArrayList<User> users = new ArrayList<User>();
		while(usersCursor.hasNext()) {
			users.add(usersCursor.next());
		}
		
		return users;
	}
	
	/**---------------------------------UPDATE---------------------------------**/
	
	public void updateScoreAdd(String username, int toAdd) throws CustomException {

		UpdateResult update = this.coll.updateOne(eq("username", username),
				new Document("$inc", new Document("score", toAdd)));
		
		if(update.wasAcknowledged() == false)
			throw new CustomException(Field.DATABASE +": la màj du score de ["+ username +"] n'a pas pu être faite (unacknowledged...).");
	}
	
	public void updateAddFriend(String username, String friendUsername) throws CustomException {
		UpdateResult update = this.coll.updateOne(eq("username", username),
				new Document("$push", new Document("friends", friendUsername)));
		
		if(update.wasAcknowledged() == false)
			throw new CustomException(Field.DATABASE +": l'ajout d'amitié a échoué dans la BDD.");
	
	}
	
	public void updateRemoveFriend(String username, String friendUsername) throws CustomException {
		UpdateResult update = this.coll.updateOne(eq("username", username),
				new Document("$pull", new Document("friends", friendUsername)));
		
		if(update.wasAcknowledged() == false)
			throw new CustomException(Field.DATABASE +": la suppression d'amitié a échoué dans la BDD.");
	
	}
	
	/**---------------------------------DELETE---------------------------------**/
	public User delete(String username, String password) {
		User deleted = this.coll.findOneAndDelete(eq("username", username));
		return deleted;
	}
	
	
}
