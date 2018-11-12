package dao.objects;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import beans.Product;
import beans.User;

public class DAOProduct {

	private  MongoCollection<Product> coll;
	
	public DAOProduct(MongoDatabase md) {
		coll = md.getCollection("Products", Product.class);
	}
	
	/**---------------------------------CREATE---------------------------------**/
	public Product create(Product p) {
		this.coll.insertOne(p);
		
		return p;
	}
	
	/**---------------------------------READ---------------------------------**/
	public Product read(String pid) {
		Bson query = com.mongodb.client.model.Filters.eq("pid", pid);
		Product product = this.coll.find(query).first();
		
		if(product == null)
			return null;
		else
			return product;
	}
	
	/**---------------------------------UPDATE---------------------------------**/
	
	/**---------------------------------DELETE---------------------------------**/
}
