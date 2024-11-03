import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Provides data related to countries from World Bank API.
 */
public class WorldBankProvider extends Provider{


    /**
     * Prints the data it can provide.
     */
    public void provide(){
        try {
            Map<String, Integer> countriesPopulation = providePopulations();
            Map<String, Integer> countriesSize = provideSizes();
            Map<String, String> countriesCapitals = provideCapitals();

            for (Map.Entry<String, Integer> entry : countriesPopulation.entrySet()) {
//                String countryName = entry.getKey();
//                System.out.println("Country: " + countryName + ". Population: " + entry.getValue() + ", Size: " + countriesSize.get(countryName));
            }

            System.out.println("I'm WorldBankProvider and I have country size, population, capitals...");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Provide all the populations of the countries in the world.
     */
    private Map<String, Integer> providePopulations() throws IOException {
        String populationUrl = "https://api.worldbank.org/v2/country/all/indicator/SP.POP.TOTL?format=json&date=2023&per_page=300";
        String populationResponse = fetchData(populationUrl);

        return parsePopulationsData(populationResponse);
    }


    /**
     * Provide all the sizes of the countries in the world.
     */
    private Map<String, Integer> provideSizes() throws IOException{
        String sizeUrl = "https://api.worldbank.org/v2/country/all/indicator/AG.SRF.TOTL.K2?format=json&date=2021&per_page=300";
        String sizeResponse = fetchData(sizeUrl);

        return parseSizesData(sizeResponse);
    }


    /**
     * Provide all the sizes of the countries in the world.
     */
    private Map<String, String> provideCapitals() throws IOException{
        String url = "https://api.worldbank.org/v2/country?format=json&per_page=300";
        String response = fetchData(url);

        return parseCapitals(response);
    }


    /**
     * Parse the population data for all the countries.
     */
    private Map<String, Integer> parsePopulationsData(String populationResponse){
        JSONArray populationArray = new JSONArray(populationResponse);
        JSONArray populationDataArray = populationArray.getJSONArray(1);

        Map<String, Integer> countriesPopulation = new HashMap<>();

        for (int i = 0; i < populationDataArray.length(); i++) {
            JSONObject populationData = populationDataArray.getJSONObject(i);
            JSONObject country = populationData.getJSONObject("country");
            String countryName = country.getString("value");
            int populationValue = populationData.optInt("value"); // May be null for missing values

            String IdValue = country.getString("id");

            if (CountriesVerifier.isValidCountry(IdValue))
                countriesPopulation.put(countryName, populationValue);
        }

        return countriesPopulation;
    }


    /**
     * Parse the sizes data for all the countries.
     */
    private Map<String, Integer> parseSizesData(String sizeResponse){
        JSONArray sizeArray = new JSONArray(sizeResponse);
        JSONArray sizeDataArray = sizeArray.getJSONArray(1);

        Map<String, Integer> countriesSize = new HashMap<>();

        for (int j = 0; j < sizeDataArray.length(); j++) {
            JSONObject sizeData = sizeDataArray.getJSONObject(j);
            JSONObject country = sizeData.getJSONObject("country");
            String countryName = country.getString("value");
            int sizeValue = sizeData.optInt("value", -1); // Use -1 if size is null

            String IdValue = country.getString("id");

            if (CountriesVerifier.isValidCountry(IdValue))
                countriesSize.put(countryName, sizeValue);
        }

        return countriesSize;
    }


    /**
     * Parse the capital cities of all the countries.
     */
    private Map<String, String> parseCapitals(String capitalResponse){
        JSONArray responseArray = new JSONArray(capitalResponse);
        JSONArray countries = responseArray.getJSONArray(1);

        Map<String, String> countriesCapitals = new HashMap<>();

        for (int j = 0; j < countries.length(); j++) {
            JSONObject country = countries.getJSONObject(j);
            String code = country.get("iso2Code").toString();
            String capitalCity = country.get("capitalCity").toString();

            if (CountriesVerifier.isValidCountry(code))
                countriesCapitals.put(code, capitalCity);
        }

        return countriesCapitals;


    }

}
