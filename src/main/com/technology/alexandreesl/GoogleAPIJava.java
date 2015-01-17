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

	final static String apiKey = "<insert your API Key here>";
	final static String customSearchEngineKey = "<insert your search engine key here>";

	// base url for the search query
	final static String searchURL = "https://www.googleapis.com/customsearch/v1?";

	public static void main(String[] args) {

		int inicio = 1;

		int contador = 0;

		while (contador < 10) {

			System.out
					.println("***************** SEARCH **************************");
			System.out.println("");

			String result = "";

			result = read("java+android", inicio, 10);

			JsonParser parser = Json.createParser(new StringReader(result));

			while (parser.hasNext()) {
				Event evento = parser.next();

				if (evento == Event.KEY_NAME) {

					if (parser.getString().equals("htmlTitle")) {
						Event valor = parser.next();

						if (valor == Event.VALUE_STRING)
							System.out.println("Title (HTML): "
									+ parser.getString());
					}

					if (parser.getString().equals("link")) {

						Event valor = parser.next();

						if (valor == Event.VALUE_STRING)
							System.out.println("Link: " + parser.getString());
					}

				}

			}

			inicio = inicio + 10;

			contador++;

			System.out
					.println("**************************************************");

		}

	}

	private static String read(String qSearch, int start, int numOfResults) {
		try {

			String toSearch = searchURL + "key=" + apiKey + "&cx="
					+ customSearchEngineKey + "&q=";

			toSearch += qSearch + "+";

			toSearch += "&alt=json";

			toSearch += "&start=" + start;

			toSearch += "&num=" + numOfResults;

			URL url = new URL(toSearch);
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

}
