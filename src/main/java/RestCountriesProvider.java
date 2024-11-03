import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Provides data related to countries from Rest Countries API.
 */
public class RestCountriesProvider extends Provider{



    public void provide(){
        try {
            System.out.println(provideCapitals());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("I'm RestCountriesProvider and I have capital cities...");
    }


    /**
     * Provide all the sizes of the countries in the world.
     */
    private Map<String, String> provideCapitals() throws IOException {
        String url = "https://restcountries.com/v3.1/all";
        String response = fetchData(url);

        return parseCapitals(response);
    }


    /**
     * Parse the capital cities from the response.
     */
    private Map<String, String> parseCapitals(String capitalResponse){
        JSONArray countriesArray = new JSONArray(capitalResponse);
        Map<String, String> countriesCapitals = new HashMap<>();

        for (int j = 0; j < countriesArray.length(); j++) {
            JSONObject country = countriesArray.getJSONObject(j);
            String code = country.getString("cca2");
            JSONArray capitals = country.optJSONArray("capital");
            String capitalCity = capitals != null && !capitals.isEmpty() ? capitals.getString(0) : "N/A";

            if (CountriesVerifier.isValidCountry(code))
                countriesCapitals.put(code, capitalCity);
        }

        return countriesCapitals;


    }



}
