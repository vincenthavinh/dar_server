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
	
	public void handleAnswer(String answer, HttpSession session, JSONObject result) throws CustomException {
		
		/*check session valide*/
		checkSession(session);
		
		/*question en cours dans la session*/
		Integer qid = (Integer) session.getAttribute(Field.QUESTION);
		if(qid == null) {
			throw new CustomException(Field.QUESTION +": aucune en cours dans la session.");
		}
		
		/*verification q&a match*/
		Question question = daoquestion.read(qid);
		Boolean rightAnswer = checkQandA(question, answer);
		
		/*si bonne reponse, augmentation du score*/
		String username = (String) session.getAttribute(Field.USERNAME);
		
		if(rightAnswer == true) {
			daouser.updateScoreAdd(username, 1);
		}
		
		
		/*JSON resultat*/
		User user = daouser.read(username);	
		
		result.put("rightAnswer", rightAnswer);
		result.put("score", user.getScore());
		
		JSONArray prods = new JSONArray();
		for(String pid : question.getProductsIds()) {
			Product p = daoproduct.read(pid);
			prods.put(new JSONObject(p));
		}			
		result.put("products", prods);
	}

	private boolean checkQandA(Question question, String answer) throws CustomException {
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
}
