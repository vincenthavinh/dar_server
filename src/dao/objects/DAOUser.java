package dao.objects;

import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import beans.User;


public class DAOUser {

	private  MongoCollection<User> coll;
	
	public DAOUser(MongoDatabase md) {
		coll = md.getCollection("Users", User.class);
	}
	
	public User create(User u) {
		this.coll.insertOne(u);;
		
		return u;
	}
	
	public User read(String username) {
		Bson query = com.mongodb.client.model.Filters.eq("username", username);
		User user = this.coll.find(query).first();
		
		if(user == null)
			return null;
		else
			return user;
	}

	public User delete(String username, String password) {
		Bson query = com.mongodb.client.model.Filters.eq("username", username);
		User deleted = this.coll.findOneAndDelete(query);
		return deleted;
	}
	
	
}
