package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;

import beans.Product;
import beans.Question;
import dao.DB;
import dao.objects.DAOProduct;
import dao.objects.DAOQuestion;
import tools.CDiscountUtils;
import tools.Field;

public class QuestionsLogic extends Logic {
	
	private static String[] keywords = {"tablette", "smartphone", "ordinateur", "cuisine", "table", "siege"};
	private static int qid_counter = new DAOQuestion(DB.get()).readHighhestQid();
	private static Random rand = new Random();

	
	private DAOProduct daoproduct = new DAOProduct(DB.get());
	private DAOQuestion daoquestion = new DAOQuestion(DB.get());
	
	private Question question;
	private List<Product> products;
	
	
	public List<Product> getProducts() {
		return products;
	}	
	
	public void newRandomQuestion(int nbProducts, HttpSession session) throws Exception {
		
		/*check session valide*/
		checkSession(session);
		
		/*creation de produits aleatoires*/
		this.products = getRandomProducts(nbProducts);
		
		/*stockage des produits dans la BDD*/
		for(Product prod : products ) {
			//on essaye de stocker dans la BDD
			try {
				daoproduct.create(prod);
				
			}catch (MongoWriteException e) {
				//si le produit est deja dans la BDD, on passe a la suite.
				if(e.getError().getCategory() == ErrorCategory.DUPLICATE_KEY) {
					System.out.println(e.getMessage());
				//si c'est une autre erreur, on remonte l'erreur.
				}else {
					throw e;
				}
			}
		}
		
		/*creation de la question avec prix aleatoire*/
		this.question = new Question();
		
		String username = (String) session.getAttribute(Field.USERNAME);
		question.setAuthor(username);
		question.setQid(this.getAndIncrementQid_counter());
		for(Product prod : products ) {
	    	question.getProductsIds().add(prod.getPid());
	    }
		question.setPrice(products.get(rand.nextInt(products.size())).getSalePrice());
		
		/*stockage de la question dans la BDD*/
		daoquestion.create(question);
		
		/*sauvegarde de l'id de la question dans la session, pour pouvoir y repondre*/
		session.setAttribute(Field.QUESTION, question.getQid());
	}

	private List<Product> getRandomProducts(int nbProducts) throws Exception {
		List<String> productidlist = new ArrayList<String>();
		
		for(int i=0; i<nbProducts; i++) {
			
			/*random productID*/
			String productid = searchRandomProductId();
			
			/*si pas deja tire avant, on l'ajoute a la liste*/
			if(productidlist.contains(productid) == false) {
				productidlist.add(productid);
			/*si produit deja tire, on refait un tour dans le for*/
			}else {
				i--;
				continue;
			}
		}
		
		List<Product> products = this.createProductsFromIds(productidlist);
		
		return products;
	}
	
	private List<Product> createProductsFromIds(List<String> productidlist) throws Exception {
		
		/*liste des produits a retourner*/
		ArrayList<Product> products = new ArrayList<Product>();
		
		/*Json renvoye par CDiscount contenant les informations des produits dont
		 * les ids ont ete passes en argument de cette fonction*/
		JSONObject json = CDiscountUtils.getProduct(productidlist);
		
		/*Pour chaque json product dans le tableau de json products de CDiscount*/
	    for(Object obj1 : ((JSONArray) json.get("Products"))){
	    	
	    	/*creation d'un objet Product a partir du json product*/
	    	JSONObject product = (JSONObject) obj1;
	    	Product p = new Product();
	    	p.setPid((String) product.get("Id"));
		    p.setName((String) product.get("Name"));
		    p.setSalePrice((String) ((JSONObject)product.get("BestOffer")).get("SalePrice"));
		    p.setProductUrl((String) ((JSONObject)product.get("BestOffer")).get("ProductURL"));
		    
		    ArrayList<String> imgsUrls = new ArrayList<String>();
		    for(Object obj2 : ((JSONArray) product.get("Images"))) {
		    	JSONObject img = (JSONObject) obj2;
		    	imgsUrls.add((String) img.get("ImageUrl"));
		    }
		    p.setImagesUrls(imgsUrls);
		    
		    /*ajout de l'objet Product cree a la liste de produits a retourner*/
		    products.add(p);
	    }
	    
	    return products;
	}
	
	private String searchRandomProductId() throws Exception {
		String randomkeyword = keywords[rand.nextInt(keywords.length)];
	    JSONObject json = CDiscountUtils.search(randomkeyword, 200, 1000);
	    
	    if(Integer.parseInt((String) json.get("ItemCount")) == 0) {
	    	System.out.println("KEYWORD: "+ randomkeyword
	    			+" | NBITEM: "+ json.get("ItemCount")
	    			+" | NBITEM: "+ json.get("ItemCount") 
	    			+" => Retry");
	    	return searchRandomProductId();
	    }
	    int randomint = rand.nextInt(((JSONArray) json.get("Products")).length());
	    System.out.println("KEYWORD: "+ randomkeyword
	    			+" | NBITEM: "+ json.get("ItemCount")
	    			+" | JSONLENGTH: "+ ((JSONArray) json.get("Products")).length()
	    			+" | RANDOMINT: "+ randomint);
	    json = (JSONObject) ((JSONArray) json.get("Products")).get(randomint);
	    return (String) json.get("Id");
	}

	private synchronized int getAndIncrementQid_counter() {
		qid_counter++;
		return qid_counter;
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		
		if(errors.isEmpty()) {
			JSONArray prods = new JSONArray();
			for(Product p : this.products) {
				prods.put(this.toJSON(p));
			}
			
			json.put("price", this.question.getPrice());
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
		
		//System.out.println(((JSONArray)json.get(("imagesUrls"))).get(0));
		
		return json;
	}
}
