package dao.objects;

import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;

import beans.User;


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
	
	/**---------------------------------DELETE---------------------------------**/
	public User delete(String username, String password) {
		User deleted = this.coll.findOneAndDelete(eq("username", username));
		return deleted;
	}
	
	
}
