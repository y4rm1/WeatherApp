import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//include JSON parser to make this work properly, as the data is in JSON format

public class Weather {
    private static final String API_KEY = System.getenv("WEATHER_API_KEY");; 
    private static final String BASE_URL = "http://api.weatherstack.com/current?access_key=";

    public static void main(String[] args) {
        
        if (API_KEY == null || API_KEY.isEmpty()) {
            System.out.println("API key is missing. ");
            return;
        }
        
        // prompts user for location to check the weather for
        System.out.println("Enter a city: ");
        Scanner scan = new Scanner(System.in);
        String location = scan.next(); 
        scan.close();

        String urlString = BASE_URL + API_KEY + "&query=" + location.replace(" ", "%20");

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");  // this is the API request method

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // read the response from the API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) { 
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response);
            } else {
                System.out.println("GET request failed, response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




