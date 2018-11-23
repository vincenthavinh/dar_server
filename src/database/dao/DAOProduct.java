package database.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import model.Product;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.*;

import java.util.Arrays;

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
	
	public String readRandomId() {
		Product product = this.coll.aggregate(
				Arrays.asList(sample(1))).first();
		
		return product.getPid();
	}
	/**---------------------------------UPDATE---------------------------------**/
	
	/**---------------------------------DELETE---------------------------------**/
}
