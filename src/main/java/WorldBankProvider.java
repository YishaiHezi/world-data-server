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
            Map<String, Double> countriesGDPs = provideGDP();
            Map<String, Double> countriesGDPsPerCapita = provideGDPPerCapita();

            for (Map.Entry<String, Integer> entry : countriesPopulation.entrySet()) {
                String countryName = entry.getKey();
                System.out.println("Country: " + countryName + ". Population: "
                        + entry.getValue() + ", Size: " + countriesSize.get(countryName));
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
    private Map<String, Double> provideGDP() throws IOException{
        String url = "https://api.worldbank.org/v2/country/all/indicator/NY.GDP.MKTP.CD?format=json&date=2023&per_page=300";
        String response = fetchData(url);

        return parseGDPs(response);
    }

    /**
     * Provide all the sizes of the countries in the world.
     */
    private Map<String, Double> provideGDPPerCapita() throws IOException{
        String url = "https://api.worldbank.org/v2/country/all/indicator/NY.GDP.PCAP.CD?format=json&date=2023&per_page=300";
        String response = fetchData(url);

        return parseGDPs(response);
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

            String IdValue = country.getString("id");

            if (CountriesVerifier.isValidCountry(IdValue)){
                int populationValue = populationData.optInt("value", -1); // May be null for missing values

                if (populationValue > 0)
                    countriesPopulation.put(countryName, populationValue);
            }
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

            String IdValue = country.getString("id");

            if (CountriesVerifier.isValidCountry(IdValue)){
                int sizeValue = sizeData.optInt("value", -1); // Use -1 if size is null

                if (sizeValue > 0)
                    countriesSize.put(countryName, sizeValue);
            }
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

            if (CountriesVerifier.isValidCountry(code)){
                String capitalCity = country.get("capitalCity").toString();
                countriesCapitals.put(code, capitalCity);
            }
        }

        return countriesCapitals;
    }


    /**
     * Parse the GDPs of all the countries.
     */
    private Map<String, Double> parseGDPs(String response){
        JSONArray responseArray = new JSONArray(response);
        JSONArray countries = responseArray.getJSONArray(1);

        Map<String, Double> countriesGDPs = new HashMap<>();

        for (int j = 0; j < countries.length(); j++) {
            JSONObject country = countries.getJSONObject(j);
            String code = country.getJSONObject("country").getString("id");

            if (CountriesVerifier.isValidCountry(code)){
                double gdpValue = country.optDouble("value", -1);

                if (gdpValue > 0)
                    countriesGDPs.put(code, gdpValue);
            }
        }

        return countriesGDPs;
    }

}
