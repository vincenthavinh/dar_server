package dao;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DB {

	private static MongoClient mc = new MongoClient();

	public static MongoDatabase get() {
		// create codec registry for POJOs
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		// get handle to "mydb" database
		return mc.getDatabase("JusteDar").withCodecRegistry(pojoCodecRegistry);
	}
}
