package dao.objects;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;

import beans.Question;

public class DAOQuestion {
	private MongoCollection<Question> coll;

	public DAOQuestion(MongoDatabase md) {
		coll = md.getCollection("Questions", Question.class);
		coll.createIndex(Indexes.descending("qid"), new IndexOptions().unique(true));
	}

	/** ---------------------------------CREATE--------------------------------- **/
	public Question create(Question q) {
		this.coll.insertOne(q);

		return q;
	}

	/** ---------------------------------READ--------------------------------- **/
	public Question read(int qid) {
		Question question = this.coll.find(eq("qid", qid)).first();

		if (question == null)
			return null;
		else
			return question;
	}

	public int readHighhestQid() {
		int highestQid = this.coll.find()
							.sort(descending("qid"))
							.projection(fields(include("qid"), excludeId()))
							.limit(1)
							.first().getQid();
		return highestQid;
	}

	/** ---------------------------------UPDATE--------------------------------- **/

	/** ---------------------------------DELETE--------------------------------- **/
}
