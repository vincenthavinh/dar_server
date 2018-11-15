package beans;

import java.util.ArrayList;
import java.util.List;

public class Question {
	
	private int qid;
	private String author;
	private List<String> productsIds;
	private String price;
	
	public Question() {
		productsIds = new ArrayList<String>();
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public List<String> getProductsIds() {
		return productsIds;
	}
	public void setProductsIds(List<String> productsIds) {
		this.productsIds = productsIds;
	}

	public int getQid() {
		return qid;
	}

	public void setQid(int qid) {
		this.qid = qid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
