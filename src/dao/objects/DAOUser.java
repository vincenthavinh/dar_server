package dao.objects;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import beans.User;
import dao.converters.UserConverter;
import dao.exceptions.DAOException;

public class DAOUser {

	private  MongoCollection<Document> coll;
	
	public DAOUser(MongoDatabase md) {
		coll = md.getCollection("Users");
	}
	
	public User create(User u) {
		Document doc = UserConverter.toDocument(u);
		this.coll.insertOne(doc);;
		ObjectId id = (ObjectId) doc.get("_id");
		u.setId(id.toString());
		return u;
	}
	
	public User read(String username) {
		Bson query = com.mongodb.client.model.Filters.eq("username", username);
		Document data = this.coll.find(query).first();
		if(data == null)
			return null;
		else
			return UserConverter.toUser(data);
	}

	public void delete(User u) throws DAOException {
		Bson query = com.mongodb.client.model.Filters.eq("_id", new ObjectId(u.getId()));
		Document deleted = this.coll.findOneAndDelete(query);
		if(deleted == null) throw new DAOException("L'utilisateur à supprimer n'a pas été trouvé.");
	}
	
	
}
