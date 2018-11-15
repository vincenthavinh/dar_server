package logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import beans.Product;
import beans.Question;
import beans.User;
import dao.DB;
import dao.objects.DAOProduct;
import dao.objects.DAOQuestion;
import dao.objects.DAOUser;

public class AnswersLogic extends Logic {

	private DAOProduct daoproduct;
	private DAOQuestion daoquestion;
	private DAOUser daouser;
	
	private String answer;
	private boolean rightAnswer;
	private User user;
	private Question question;
	public AnswersLogic(HttpServletRequest req) {
		super(req);
		daoproduct = new DAOProduct(DB.get());
		daoquestion = new DAOQuestion(DB.get());
		daouser = new DAOUser(DB.get());
	}

	public void handleAnswer() {
		
		/*check session valide*/
		HttpSession session = getValidSession();
		if(session == null) {
			return;
		}
		
		/*question en cours dans la session*/
		Integer qid = (Integer) session.getAttribute(QUESTION_FIELD);
		if(qid == null) {
			errors.put(QUESTION_FIELD, "pas de question en cours dans la session");
			return;
		}
		
		/*reponse*/
		answer = (String) req.getParameter(ANSWER_FIELD);
		
		/*verification q&a match*/
		rightAnswer = checkQandA(qid, answer);
		
		/*si bonne reponse, augmentation du score*/
		String username = (String) session.getAttribute(USERNAME_FIELD);
		
		if(rightAnswer == true) {
			try {
				daouser.updateScoreAdd(username, 1);
			} catch (Exception e) {
				errors.put(DATABASE_FIELD, e.getMessage());
			}
		}
		
		/*resultat*/
		user = daouser.read(username);
	}

	private boolean checkQandA(int qid, String answer) {
		question = daoquestion.read(qid);
		Product product = daoproduct.read(answer);
		
		if(question.getProductsIds().contains(answer) 
				&& question.getPrice().equals(product.getSalePrice())) {
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
				prods.add(this.toJSON(p));
			}			
			json.put("products", prods);
		}
		
		return json;
	}
	
	private JSONObject toJSON(Product p) {
		JSONArray imgs = new JSONArray();
		imgs.addAll(p.getImagesUrls());
		
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
