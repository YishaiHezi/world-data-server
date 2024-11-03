import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Provides data related to countries from Rest Countries API.
 */
public class RestCountriesProvider extends Provider{



    public void provide(){
        try {
            System.out.println(provideAllData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("I'm RestCountriesProvider and I have capital cities...");
    }


    /**
     * Provide all the sizes of the countries in the world.
     */
    private List<Map<String, String>> provideAllData() throws IOException {
        String url = "https://restcountries.com/v3.1/all";
        String response = fetchData(url);

        return parseData(response);
    }


    /**
     * Parse the data from the response.
     */
    private List<Map<String, String>> parseData(String capitalResponse){
        JSONArray countriesArray = new JSONArray(capitalResponse);
        Map<String, String> codesToNames = new HashMap<>();
        Map<String, String> codesToCapitals = new HashMap<>();

        for (int j = 0; j < countriesArray.length(); j++) {

            // Get country code:
            JSONObject country = countriesArray.getJSONObject(j);
            String code = country.getString("cca2");

            // Get country capital:
            JSONArray capitals = country.optJSONArray("capital");
            String capitalCity = capitals != null && !capitals.isEmpty() ? capitals.getString(0) : "N/A";


            // Get the common country name:
            JSONObject nameObject = country.optJSONObject("name");
            String commonName = nameObject != null ? nameObject.optString("common", "N/A") : "N/A";

            if (CountriesVerifier.isValidCountry(code)) {
                codesToNames.put(code, commonName);
                codesToCapitals.put(code, capitalCity);
            }
        }

        return Arrays.asList(codesToNames, codesToCapitals);


    }



}
