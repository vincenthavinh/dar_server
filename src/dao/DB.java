package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DB {
	
	private static DB instance;
	
	private static MongoClient mc;
	
	private DB() {
		mc = new MongoClient();
	}
	
	public static MongoDatabase get() {
		if(instance == null) instance = new DB();
		
		return mc.getDatabase("JusteDar");
	}
}
