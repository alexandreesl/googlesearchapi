package com.technology.alexandreesl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class GoogleAPIJava {

	final static String apiKey = "<insert api key>";
	final static String customSearchEngineKey = "<insert custom search engine key>";

	// base url for the search query
	final static String searchURL = "https://www.googleapis.com/customsearch/v1?";

	public static void main(String[] args) {

		int inicio = 1;

		int contador = 0;

		while (contador < 11) {
			
			System.out.println("***************** CONSULTA **************************");
			System.out.println("");

			String result = "";

			result = read(makeSearchString("pernambucanas", inicio, 10));

			System.out.println(result);

			JsonParser parser = Json.createParser(new StringReader(result));
			while (parser.hasNext()) {
				Event evento = parser.next();
				switch (evento) {
				case KEY_NAME: {
					System.out.print(parser.getString() + "=");
					break;
				}
				case VALUE_STRING: {
					System.out.println(parser.getString());
					break;
				}
				case VALUE_NUMBER: {
					System.out.println(parser.getString());
					break;
				}
				case VALUE_NULL: {
					System.out.println("null");
					break;
				}
				case START_ARRAY: {
					System.out.println("Inicio do Array");
					break;
				}
				case END_ARRAY: {
					System.out.println("Final do Array");
					break;
				}
				case END_OBJECT: {
					System.out.println("Final do Objeto Json");
					break;
				}
				}
			}

			inicio = inicio + 10;

			contador++;
			
			System.out.println("**************************************************");

		}

	}

	private static String read(String pUrl) {
		// pUrl is the URL we created in previous step
		try {
			URL url = new URL(pUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String makeSearchString(String qSearch, int start,
			int numOfResults) {
		String toSearch = searchURL + "key=" + apiKey + "&cx="
				+ customSearchEngineKey + "&q=";

		// replace spaces in the search query with +
		String keys[] = qSearch.split("[ ]+");
		for (String key : keys) {
			toSearch += key + "+"; // append the keywords to the url
		}

		// specify response format as json
		toSearch += "&alt=json";

		// specify starting result number
		toSearch += "&start=" + start;

		// specify the number of results you need from the starting position
		toSearch += "&num=" + numOfResults;

		return toSearch;
	}

}
