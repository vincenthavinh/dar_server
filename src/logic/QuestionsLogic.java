package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;import com.sun.imageio.plugins.png.PNGImageReaderSpi;

import beans.Product;
import beans.Question;
import dao.DB;
import dao.objects.DAOProduct;
import dao.objects.DAOQuestion;
import tools.CDiscountUtils;

public class QuestionsLogic extends Logic {
	
	private DAOProduct daoproduct;
	private DAOQuestion daoquestion;
	
	private Question question;
	private List<Product> products;
	
	private static String[] keywords = {"tablette", "smartphone", "ordinateur", "cuisine", "table", "siege"};
	private static Random rand = new Random();
	private static int qid_counter = new DAOQuestion(DB.get()).readHighhestQid();
	
	public QuestionsLogic(HttpServletRequest req) {
		super(req);
		daoproduct = new DAOProduct(DB.get());
		daoquestion = new DAOQuestion(DB.get());
	}

	public List<Product> getProducts() {
		return products;
	}	
	
	public void newRandomQuestion(int nbProducts) {
		
		/*check session valide*/
		if(getValidSession() == null) {
			return;
		}
		
		/*creation de produits aleatoires*/
		this.products = getRandomProducts(nbProducts);
		
		/*stockage des produits dans la BDD*/
		for(Product prod : products ) {
			//on essaye de stocker dans la BDD
			try {
				daoproduct.create(prod);
			//si une erreur est levee c'est parce que le produit est deja dans la BDD, 
			//on passe donc a la suite
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		/*creation de la question avec prix aleatoire*/
		this.question = new Question();
		String username = (String) req.getSession(false).getAttribute(USERNAME_FIELD);
		question.setAuthor(username);
		question.setQid(this.getAndIncrementQid_counter());
		for(Product prod : products ) {
	    	question.getProductsIds().add(prod.getPid());
	    }
		question.setPrice(products.get(rand.nextInt(products.size())).getSalePrice());
		
		/*stockage de la question dans la BDD*/
		daoquestion.create(question);
		
		/*sauvegarde de l'id de la question dans la session, pour pouvoir y repondre*/
		getValidSession().setAttribute(QUESTION_FIELD, question.getQid());
	}

	private List<Product> getRandomProducts(int nbProducts) {
		List<String> productidlist = new ArrayList<String>();
		
		for(int i=0; i<nbProducts; i++) {
		    productidlist.add(this.searchRandomProductId());
		}
		
		List<Product> products = this.createProductsFromIds(productidlist);
		
		return products;
	}
	
	private List<Product> createProductsFromIds(List<String> productidlist) {
		ArrayList<Product> products = new ArrayList<Product>();
		
		JSONObject json = CDiscountUtils.getProduct(productidlist);
	    for(Object object : ((JSONArray) json.get("Products"))){
	    	JSONObject product = (JSONObject) object;
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
		    
		    products.add(p);
	    }
	    
	    return products;
	}
	
	private String searchRandomProductId() {
		String randomkeyword = keywords[rand.nextInt(keywords.length)];
	    JSONObject json = CDiscountUtils.search(randomkeyword, 200, 1000);
	    
	    if(Integer.parseInt((String) json.get("ItemCount")) == 0) {
	    	System.out.println("KEYWORD: "+ randomkeyword
	    			+" | NBITEM: "+ json.get("ItemCount")
	    			+" | NBITEM: "+ json.get("ItemCount") 
	    			+" => Retry");
	    	return searchRandomProductId();
	    }
	    int randomint = rand.nextInt(((JSONArray) json.get("Products")).size());
	    System.out.println("KEYWORD: "+ randomkeyword
	    			+" | NBITEM: "+ json.get("ItemCount")
	    			+" | JSONLENGTH: "+ ((JSONArray) json.get("Products")).size()
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
				prods.add(this.toJSON(p));
			}
			
			json.put("price", this.question.getPrice());
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
		
		//System.out.println(((JSONArray)json.get(("imagesUrls"))).get(0));
		
		return json;
	}
}
