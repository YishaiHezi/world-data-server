import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;


/**
 * Provides the data for the countries.
 */
public class CountryDataProvider {

    public static void main(String[] args) {

        try {
            Set<String> countriesIds = getCountriesCodes();
            Map<String, Integer> countriesPopulation = providePopulations(countriesIds);
            Map<String, Integer> countriesSize = provideSizes(countriesIds);

            for (Map.Entry<String, Integer> entry : countriesPopulation.entrySet()) {
                String countryName = entry.getKey();
                System.out.println("Country: " + countryName + ", Population: " + entry.getValue() + ", Size: " + countriesSize.get(countryName));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Provide all the populations of the countries in the world.
     */
    private static Map<String, Integer> providePopulations(Set<String> countriesIds) throws IOException{
        String populationUrl = "https://api.worldbank.org/v2/country/all/indicator/SP.POP.TOTL?format=json&date=2023&per_page=300";
        String populationResponse = fetchData(populationUrl);

        return parsePopulationsData(populationResponse, countriesIds);
    }


    /**
     * Provide all the sizes of the countries in the world.
     */
    private static Map<String, Integer> provideSizes(Set<String> countriesIds) throws IOException{
        String sizeUrl = "https://api.worldbank.org/v2/country/all/indicator/AG.SRF.TOTL.K2?format=json&date=2021&per_page=300";
        String sizeResponse = fetchData(sizeUrl);

        return parseSizesData(sizeResponse, countriesIds);
    }


    /**
     * Gets the data from the given url.
     */
    private static String fetchData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            if (response.body() != null)
                return response.body().string();
            else
                throw new IOException("response.body() is null!");
        }
    }


    /**
     * Returns a list of all the countries codes / ids in the world.
     */
    private static Set<String> getCountriesCodes(){
        String codes = "AF	AL	DZ	AS	AD	AO	AI	AQ	AG	AR	AM	AW	AU	AT	AZ  BS	BH	BD	BB	BY	BE	BZ	BJ	BM	BT	BO	BQ	BA	BW	BV	BR	IO	BN	BG	BF	BI	CV	KH	CM	CA	KY	CF	TD	CL	CN	CX	CC	CO	KM	CD	CG	CK	CR	HR	CU	CW	CY	CZ	CI	DK	DJ	DM	DO	EC	EG	SV	GQ	ER	EE	SZ	ET	FK	FO	FJ	FI	FR	GF	PF	TF	GA	GM	GE	DE	GH	GI	GR	GL	GD	GP	GU	GT	GG	GN	GW	GY	HT	HM	VA	HN	HK	HU	IS	IN	ID	IR	IQ	IE	IM	IL	IT	JM	JP	JE	JO	KZ	KE	KI	KP	KR	KW	KG	LA	LV	LB	LS	LR	LY	LI	LT	LU	MO	MG	MW	MY	MV	ML	MT	MH	MQ	MR	MU	YT	MX	FM	MD	MC	MN	ME	MS	MA	MZ	MM	NA	NR	NP	NL	NC	NZ	NI	NE	NG	NU	NF	MP	NO	OM	PK	PW	PS	PA	PG	PY	PE	PH	PN	PL	PT	PR	QA	MK	RO	RU	RW	RE	BL	SH	KN	LC	MF	PM	VC	WS	SM	ST	SA	SN	RS	SC	SL	SG	SX	SK	SI	SB	SO	ZA	GS	SS	ES	LK	SD	SR	SJ	SE	CH	SY	TW	TJ	TZ	TH	TL	TG	TK	TO	TT	TN	TR	TM	TC	TV	UG	UA	AE	GB	UM	US	UY	UZ	VU	VE	VN	VG	VI	WF	EH	YE	ZM	ZW	AX";

        return new HashSet<>(Arrays.asList(codes.split("\t")));
    }


    /**
     * Parse the population data for all the countries.
     */
    private static Map<String, Integer> parsePopulationsData(String populationResponse, Set<String> countriesIds){
        JSONArray populationArray = new JSONArray(populationResponse);
        JSONArray populationDataArray = populationArray.getJSONArray(1);

        Map<String, Integer> countriesPopulation = new HashMap<>();

        for (int i = 0; i < populationDataArray.length(); i++) {
            JSONObject populationData = populationDataArray.getJSONObject(i);
            JSONObject country = populationData.getJSONObject("country");
            String countryName = country.getString("value");
            int populationValue = populationData.optInt("value"); // May be null for missing values

            String IdValue = country.getString("id");

            if (countriesIds.contains(IdValue))
                countriesPopulation.put(countryName, populationValue);
        }

        return countriesPopulation;
    }


    /**
     * Parse the sizes data for all the countries.
     */
    private static Map<String, Integer> parseSizesData(String sizeResponse, Set<String> countriesIds){
        JSONArray sizeArray = new JSONArray(sizeResponse);
        JSONArray sizeDataArray = sizeArray.getJSONArray(1);

        Map<String, Integer> countriesSize = new HashMap<>();

        for (int j = 0; j < sizeDataArray.length(); j++) {
            JSONObject sizeData = sizeDataArray.getJSONObject(j);
            JSONObject country = sizeData.getJSONObject("country");
            String countryName = country.getString("value");
            int sizeValue = sizeData.optInt("value", -1); // Use -1 if size is null

            String IdValue = country.getString("id");

            if (countriesIds.contains(IdValue))
                countriesSize.put(countryName, sizeValue);
        }

        return countriesSize;
    }

}
