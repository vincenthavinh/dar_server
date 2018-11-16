package dao.objects;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import static com.mongodb.client.model.Filters.*;
import beans.Product;

public class DAOProduct {

	private  MongoCollection<Product> coll;
	
	public DAOProduct(MongoDatabase md) {
		coll = md.getCollection("Products", Product.class);
		coll.createIndex(Indexes.ascending("pid"), new IndexOptions().unique(true));
	}
	
	/**---------------------------------CREATE---------------------------------**/
	public Product create(Product p) {
		this.coll.insertOne(p);
		
		return p;
	}
	
	/**---------------------------------READ---------------------------------**/
	public Product read(String pid) {
		Product product = this.coll.find(eq("pid", pid)).first();
		
		if(product == null)
			return null;
		else
			return product;
	}
	
	/**---------------------------------UPDATE---------------------------------**/
	
	/**---------------------------------DELETE---------------------------------**/
}
