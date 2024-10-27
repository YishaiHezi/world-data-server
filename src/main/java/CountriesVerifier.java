import java.util.*;


/**
 * Responsible to verify if a code belongs to a real country.
 */
public class CountriesVerifier {


    /**
     * All the ids / codes of all the countries in the world.
     */
    Set<String> countriesCodes = new HashSet<>(Arrays.asList("AF", "AL", "DZ", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BQ", "BA", "BW", "BV", "BR", "IO", "BN", "BG", "BF", "BI", "CV", "KH", "CM", "CA", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO", "KM", "CD", "CG", "CK", "CR", "HR", "CU", "CW", "CY", "CZ", "CI", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ", "ER", "EE", "SZ", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE", "GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP", "JE", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "MK", "RO", "RU", "RW", "RE", "BL", "SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SX", "SK", "SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD", "SR", "SJ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TK", "TO", "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "UM", "US", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE", "ZM", "ZW", "AX"));

    Map<String, String> countryNameToCode = new HashMap<>() {{
        put("PR", "Puerto Rico");
        put("PS", "West Bank and Gaza");
        put("PT", "Portugal");
        put("PW", "Palau");
        put("PY", "Paraguay");
        put("QA", "Qatar");
        put("AD", "Andorra");
        put("AE", "United Arab Emirates");
        put("AF", "Afghanistan");
        put("AG", "Antigua and Barbuda");
        put("AL", "Albania");
        put("AM", "Armenia");
        put("AO", "Angola");
        put("AR", "Argentina");
        put("AS", "American Samoa");
        put("AT", "Austria");
        put("AU", "Australia");
        put("AW", "Aruba");
        put("RO", "Romania");
        put("BA", "Bosnia and Herzegovina");
        put("BB", "Barbados");
        put("RS", "Serbia");
        put("BD", "Bangladesh");
        put("BE", "Belgium");
        put("RU", "Russian Federation");
        put("BF", "Burkina Faso");
        put("BG", "Bulgaria");
        put("RW", "Rwanda");
        put("BH", "Bahrain");
        put("BI", "Burundi");
        put("BJ", "Benin");
        put("BM", "Bermuda");
        put("BN", "Brunei Darussalam");
        put("BO", "Bolivia");
        put("SA", "Saudi Arabia");
        put("SB", "Solomon Islands");
        put("BR", "Brazil");
        put("SC", "Seychelles");
        put("SD", "Sudan");
        put("BT", "Bhutan");
        put("SE", "Sweden");
        put("SG", "Singapore");
        put("BW", "Botswana");
        put("SI", "Slovenia");
        put("BY", "Belarus");
        put("BZ", "Belize");
        put("SK", "Slovak Republic");
        put("SL", "Sierra Leone");
        put("SM", "San Marino");
        put("SN", "Senegal");
        put("SO", "Somalia");
        put("CA", "Canada");
        put("SR", "Suriname");
        put("SS", "South Sudan");
        put("CD", "Congo, Dem. Rep.");
        put("ST", "Sao Tome and Principe");
        put("CF", "Central African Republic");
        put("SV", "El Salvador");
        put("CG", "Congo, Rep.");
        put("SX", "Sint Maarten (Dutch part)");
        put("CH", "Switzerland");
        put("CI", "Cote d'Ivoire");
        put("SY", "Syrian Arab Republic");
        put("SZ", "Eswatini");
        put("CL", "Chile");
        put("CM", "Cameroon");
        put("CN", "China");
        put("CO", "Colombia");
        put("CR", "Costa Rica");
        put("TC", "Turks and Caicos Islands");
        put("TD", "Chad");
        put("CU", "Cuba");
        put("CV", "Cabo Verde");
        put("TG", "Togo");
        put("CW", "Curacao");
        put("TH", "Thailand");
        put("CY", "Cyprus");
        put("TJ", "Tajikistan");
        put("CZ", "Czechia");
        put("TL", "Timor-Leste");
        put("TM", "Turkmenistan");
        put("TN", "Tunisia");
        put("TO", "Tonga");
        put("TR", "Turkiye");
        put("TT", "Trinidad and Tobago");
        put("DE", "Germany");
        put("TV", "Tuvalu");
        put("DJ", "Djibouti");
        put("TZ", "Tanzania");
        put("DK", "Denmark");
        put("DM", "Dominica");
        put("DO", "Dominican Republic");
        put("UA", "Ukraine");
        put("UG", "Uganda");
        put("DZ", "Algeria");
        put("EC", "Ecuador");
        put("US", "United States");
        put("EE", "Estonia");
        put("EG", "Egypt, Arab Rep.");
        put("UY", "Uruguay");
        put("UZ", "Uzbekistan");
        put("ER", "Eritrea");
        put("VC", "St. Vincent and the Grenadines");
        put("ES", "Spain");
        put("ET", "Ethiopia");
        put("VE", "Venezuela, RB");
        put("VG", "British Virgin Islands");
        put("VI", "Virgin Islands (U.S.)");
        put("VN", "Viet Nam");
        put("VU", "Vanuatu");
        put("FI", "Finland");
        put("FJ", "Fiji");
        put("FM", "Micronesia, Fed. Sts.");
        put("FO", "Faroe Islands");
        put("FR", "France");
        put("GA", "Gabon");
        put("GB", "United Kingdom");
        put("WS", "Samoa");
        put("GD", "Grenada");
        put("GE", "Georgia");
        put("GH", "Ghana");
        put("GI", "Gibraltar");
        put("GL", "Greenland");
        put("GM", "Gambia, The");
        put("GN", "Guinea");
        put("GQ", "Equatorial Guinea");
        put("GR", "Greece");
        put("GT", "Guatemala");
        put("GU", "Guam");
        put("GW", "Guinea-Bissau");
        put("GY", "Guyana");
        put("HK", "Hong Kong SAR, China");
        put("HN", "Honduras");
        put("HR", "Croatia");
        put("HT", "Haiti");
        put("YE", "Yemen, Rep.");
        put("HU", "Hungary");
        put("ID", "Indonesia");
        put("IE", "Ireland");
        put("IL", "Israel");
        put("IM", "Isle of Man");
        put("IN", "India");
        put("ZA", "South Africa");
        put("IQ", "Iraq");
        put("IR", "Iran, Islamic Rep.");
        put("IS", "Iceland");
        put("IT", "Italy");
        put("ZM", "Zambia");
        put("ZW", "Zimbabwe");
        put("JM", "Jamaica");
        put("JO", "Jordan");
        put("JP", "Japan");
        put("KE", "Kenya");
        put("KG", "Kyrgyz Republic");
        put("KH", "Cambodia");
        put("KI", "Kiribati");
        put("KM", "Comoros");
        put("KN", "St. Kitts and Nevis");
        put("KP", "Korea, Dem. People's Rep.");
        put("KR", "Korea, Rep.");
        put("KW", "Kuwait");
        put("KY", "Cayman Islands");
        put("KZ", "Kazakhstan");
        put("LA", "Lao PDR");
        put("LB", "Lebanon");
        put("LC", "St. Lucia");
        put("LI", "Liechtenstein");
        put("LK", "Sri Lanka");
        put("LR", "Liberia");
        put("LS", "Lesotho");
        put("LT", "Lithuania");
        put("LU", "Luxembourg");
        put("LV", "Latvia");
        put("LY", "Libya");
        put("MA", "Morocco");
        put("MC", "Monaco");
        put("MD", "Moldova");
        put("ME", "Montenegro");
        put("MF", "St. Martin (French part)");
        put("MG", "Madagascar");
        put("MH", "Marshall Islands");
        put("MK", "North Macedonia");
        put("ML", "Mali");
        put("MM", "Myanmar");
        put("MN", "Mongolia");
        put("MO", "Macao SAR, China");
        put("MP", "Northern Mariana Islands");
        put("MR", "Mauritania");
        put("MT", "Malta");
        put("MU", "Mauritius");
        put("MV", "Maldives");
        put("MW", "Malawi");
        put("MX", "Mexico");
        put("MY", "Malaysia");
        put("MZ", "Mozambique");
        put("NA", "Namibia");
        put("NC", "New Caledonia");
        put("NE", "Niger");
        put("NG", "Nigeria");
        put("NI", "Nicaragua");
        put("NL", "Netherlands");
        put("NO", "Norway");
        put("NP", "Nepal");
        put("NR", "Nauru");
        put("NZ", "New Zealand");
        put("OM", "Oman");
        put("PA", "Panama");
        put("PE", "Peru");
        put("PF", "French Polynesia");
        put("PG", "Papua New Guinea");
        put("PH", "Philippines");
        put("PK", "Pakistan");
        put("PL", "Poland");
    }};


    /**
     * Verifies the given code. Checks if it belongs to a real country.
     */
    public boolean isValidCountry(String code){
        return countriesCodes.contains(code);
    }


    /**
     * Print relevant things of the verifier.
     */
    private void printMe(){
        System.out.println("Countries codes: " + countriesCodes);
        System.out.println("Number of countries: " + countriesCodes.size());




    }




}
