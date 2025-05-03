import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;

public class Call_API {
	
	private static String apiKey="";
	private static String prompt="";
	private static String schema="";
	
	public Call_API(String apiKey, String schema, String prompt) {
		this.apiKey= apiKey;
		this.prompt= prompt;
		this.schema= schema;
		
	}
	
// -----------------------------------create http request and get response in this method-------------------------------------------------------------------
	
	public static String  getResponse(){
		String model = "gpt-3.5-turbo";
		 String responseBody ="";
		
		try {
            // Create an HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Set the API endpoint URL
            URI uri = URI.create("https://api.openai.com/v1/chat/completions");

            // Create the JSON payload with the input prompt and model parameter
            String jsonPayload = "{ \"model\": \"" + model + "\", " +
                                 "\"messages\": [{ \"role\": \"system\", \"content\": \"" + schema + "\" }, " +
                                                "{ \"role\": \"user\", \"content\": \"" + prompt + "\" }]}";

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response status code and body
            int statusCode = response.statusCode();
            if(statusCode==401)System.out.print("Invalid API Key, please try with different key");
            responseBody = response.body();
            
		}catch (Exception e) {
            e.printStackTrace();
            
        }
		return parseJSON(responseBody);
		
	}
	
	private static String parseJSON(String responseBody) {
		
		try (JsonReader reader = Json.createReader(new StringReader(responseBody))) {
            // Parse JSON string to JsonObject
            JsonObject jsonObject = reader.readObject();

            // Access individual elements in the JSON
            String jsonQuery = jsonObject.getJsonArray("choices").getJsonObject(0).getJsonObject("message").getString("content");
            return jsonQuery;
            		
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	
}

