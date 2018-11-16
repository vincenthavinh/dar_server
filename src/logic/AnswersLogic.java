package logic;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import beans.Product;
import beans.Question;
import beans.User;
import dao.DB;
import dao.objects.DAOProduct;
import dao.objects.DAOQuestion;
import dao.objects.DAOUser;
import tools.CustomException;
import tools.Field;

public class AnswersLogic extends Logic {

	private DAOProduct daoproduct = new DAOProduct(DB.get());
	private DAOQuestion daoquestion = new DAOQuestion(DB.get());
	private DAOUser daouser = new DAOUser(DB.get());
	
	private boolean rightAnswer;
	private User user;
	private Question question;

	public void handleAnswer(String answer, HttpSession session) throws CustomException {
		
		/*check session valide*/
		checkSession(session);
		
		/*question en cours dans la session*/
		Integer qid = (Integer) session.getAttribute(Field.QUESTION);
		if(qid == null) {
			throw new CustomException(Field.QUESTION +": aucune en cours dans la session.");
		}
		
		/*verification q&a match*/
		rightAnswer = checkQandA(qid, answer);
		
		/*si bonne reponse, augmentation du score*/
		String username = (String) session.getAttribute(Field.USERNAME);
		
		if(rightAnswer == true) {
			daouser.updateScoreAdd(username, 1);
		}
		
		/*resultat*/
		user = daouser.read(username);
	}

	private boolean checkQandA(int qid, String answer) throws CustomException {
		question = daoquestion.read(qid);
		
		if(answer == null || answer.equals("")) {
			throw new CustomException(Field.ANSWER +": manquante dans la requête.");
		}
		
		if(question.getProductsIds().contains(answer) == false) {
			throw new CustomException(Field.ANSWER +": le produit répondu ["+ answer +"] ne fait pas partie des produits de la question.");
		}
		
		Product product = daoproduct.read(answer);

		if(question.getPrice().equals(product.getSalePrice())) {
			return true;
		}else {
			return false;
		}
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		
		if(errors.isEmpty()) {
			json.put("rightAnswer", rightAnswer);
			json.put("score", user.getScore());
			
			JSONArray prods = new JSONArray();
			for(String pid : this.question.getProductsIds()) {
				Product p = daoproduct.read(pid);
				prods.put(this.toJSON(p));
			}			
			json.put("products", prods);
		}
		
		return json;
	}
	
	private JSONObject toJSON(Product p) {
		JSONArray imgs = new JSONArray(p.getImagesUrls());
		
		JSONObject json = new JSONObject();
		json.put("name", p.getName());
		json.put("pid", p.getPid());
		json.put("imagesUrls", imgs);
		json.put("productUrl", p.getProductUrl());
		json.put("salePrice", p.getSalePrice());
		
		//System.out.println(((JSONArray)json.get(("imagesUrls"))).get(0));
		
		return json;
	}
}
