package dao.objects;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
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
	public User read(String username) {
		User user = this.coll.find(eq("username", username)).first();
				
		if(user == null)
			return null;
		else
			return user;
	}

	/**---------------------------------UPDATE---------------------------------**/
	
	public void updateScoreAdd(String username, int toAdd) throws CustomException {

		UpdateResult update = this.coll.updateOne(eq("username", username),
				new Document("$inc", new Document("score", toAdd)));
		
		if(update.wasAcknowledged() == false)
			throw new CustomException(Field.DATABASE +": la maj du score de ["+ username +"] n'a pas pu être faite (unacknowledged...).");
	}
	
	/**---------------------------------DELETE---------------------------------**/
	public User delete(String username, String password) {
		User deleted = this.coll.findOneAndDelete(eq("username", username));
		return deleted;
	}
	
	
}
