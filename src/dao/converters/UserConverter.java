package dao.converters;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

import beans.User;

public class UserConverter {

	//serialise le bean User en un DBObject pour le stocker dans mongodb
	public static Document toDocument(User u) {
		Document doc = new Document();
		doc.append("username", u.getUsername());
		doc.append("password", u.getPassword());
		if (u.getId() != null)
			doc.append("_id", new ObjectId(u.getId()));
		return doc;
	}
	
	//cree un bean User a partir du DBObject dans mongodb
	public static User toUser(Document doc) {
		User u = new User();
		u.setUsername((String) doc.getString("username"));
		u.setPassword((String) doc.getString("password"));
		ObjectId id = (ObjectId) doc.getObjectId("_id");
		u.setId(id.toString());
		return u;
	}
	
}
