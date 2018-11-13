package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}
