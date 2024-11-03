import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Writes the given maps to a json file.
 */
public class JsonWriter {


    /**
     * Writes the given maps to a json file.
     */
    public void writeToJsonFile(@NotNull Map<String, String> countriesCodes,
                                @Nullable Map<String, Integer> countriesPopulation,
                                @Nullable Map<String, Integer> countriesSize,
                                @Nullable Map<String, String> countriesCapitals,
                                @Nullable Map<String, Double> countriesGDPs,
                                @Nullable Map<String, Double> countriesGDPsPerCapita){

        JSONArray countriesArray = new JSONArray();

        // Iterate through the country codes
        Set<String> countryCodes = countriesCodes.keySet();
        for (String code : countryCodes) {
            JSONObject countryObject = new JSONObject();

            countryObject.put("countryCode", code);
            countryObject.put("name", countriesCodes.get(code));

            if (countriesCapitals != null && countriesCapitals.containsKey(code)) {
                countryObject.put("capital", countriesCapitals.get(code));
            }

            if (countriesPopulation != null && countriesPopulation.containsKey(code)) {
                countryObject.put("population", countriesPopulation.get(code));
            }

            if (countriesSize != null && countriesSize.containsKey(code)) {
                countryObject.put("area", countriesSize.get(code));
            }

            if (countriesGDPs != null && countriesGDPs.containsKey(code)) {
                countryObject.put("GDP", countriesGDPs.get(code));
            }

            if (countriesGDPsPerCapita != null && countriesGDPsPerCapita.containsKey(code)) {
                countryObject.put("GDPPerCapita", countriesGDPsPerCapita.get(code));
            }

            countriesArray.put(countryObject);
        }

        // Write the JSON array to a file
        try (FileWriter file = new FileWriter("countriesData.json")) {
            file.write(countriesArray.toString(2)); // Indentation level of 2 for pretty-printing
            file.flush();
            System.out.println("JSON file created: countriesData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
