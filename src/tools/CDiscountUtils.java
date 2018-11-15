package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CDiscountUtils {

	private static String apiKey;

	public static void setApiKey(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		apiKey = br.readLine();
		br.close();
	}

	public static String getApiKey() {
		return apiKey;
	}

	public static JSONObject search(String keywords, int minPrice, int maxPrice) {
		
		/* Creation du JSON requete a envoyer a l'API CDiscount */
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("ApiKey", apiKey);
		
		JSONObject search = new JSONObject();
		jsonReq.put("SearchRequest", search);
		{
			search.put("Keyword", keywords);
			search.put("SortBy", "relevance");

			JSONObject pagin = new JSONObject();
			search.put("Pagination", pagin);
			{
				pagin.put("ItemsPerPage", 10);
				pagin.put("PageNumber", 0);
			}

			JSONObject filters = new JSONObject();
			search.put("Filters", filters);
			{
				JSONObject price = new JSONObject();
				filters.put("Price", price);
				{
					price.put("Min", minPrice);
					price.put("Max", maxPrice);
				}

				filters.put("Navigation", "");
				filters.put("IncludeMarketPlace", false);
				filters.put("Condition", null);
			}
		}
		
		/*envoi de la requete et reception de la reponse*/
		return sendToCDiscount(jsonReq, "https://api.cdiscount.com/OpenApi/json/Search");
	}
	
	public static JSONObject getProduct(List<String> productIdList) {
		
		/* Creation du JSON requete a envoyer a l'API CDiscount */
		JSONObject jsonReq = new JSONObject();
		jsonReq.put("ApiKey", apiKey);
		
		JSONObject product = new JSONObject();
		jsonReq.put("ProductRequest", product);
		{
			JSONArray list = new JSONArray();
			product.put("ProductIdList", list);
			{
				for(String id : productIdList) list.add(id);
			}
			
			JSONObject scope = new JSONObject();
			product.put("Scope", scope);
			{
				scope.put("Offers", false);
				scope.put("AssociatedProducts", false);
				scope.put("Images", true);
				scope.put("EAN", false);
			}
		}

		/*envoi de la requete et reception de la reponse*/
		return sendToCDiscount(jsonReq, "https://api.cdiscount.com/OpenApi/json/GetProduct");
	}
	
	private static JSONObject sendToCDiscount(JSONObject request, String urlString) {
		JSONObject response = null;
		
		/* https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection */
		try {
			//Phase de connexion
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			
			//configuration de la requete
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			
			//envoi de la requete
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(request.toJSONString());
			wr.flush();
			
			//reception de la reponse
			StringBuilder sb = new StringBuilder();  
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				//ecriture de la reponse dans un StringBuilder
			    BufferedReader br = new BufferedReader(
			            new InputStreamReader(con.getInputStream(), "utf-8"));
			    String line = null;  
			    while ((line = br.readLine()) != null) {  
			        sb.append(line + "\n");  
			    }
			    br.close();
			    
			    //conversion Stringbuilder -> JSONObject
			    JSONParser parser = new JSONParser();
				response = (JSONObject) parser.parse(sb.toString());
			} else {
				Thread.sleep(1000);
				return sendToCDiscount(request, urlString);
//			    throw new Exception("(Echech) Code Retour appel CDiscount :" 
//			    		+con.getResponseMessage());
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
