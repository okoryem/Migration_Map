package com.migration_map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class CountryCodes {
    private Map<String, String> codes = new HashMap<>();
    private Map<String, String[]> codes2 = new HashMap<>();
    private final File csvFile = new File("all.csv");
    private final ArrayList<String> countriesNames = new ArrayList<>();
    private final Map<String, String> namesToCode = new HashMap<>();

    CountryCodes() {
        loadFile();
    }

    /*
    public ArrayList<String> getCountriesNames() {
        return countriesNames;
    }

    public Map<String, String> getNamesToCode() {
        return namesToCode;
    }

     */

    private void loadFile() {
        try {
            Scanner fileReader = new Scanner(csvFile);
            String hedaerRow = fileReader.nextLine();

            while (fileReader.hasNextLine()) {
                String countryInfo = fileReader.nextLine();
                String[] countryInfoSplit = countryInfo.split(",");

                String[] countryArray;
                countryArray = new String[3];
                countryArray[0] = countryInfoSplit[0];
                countryArray[1] = countryInfoSplit[2];

                codes.put(countryInfoSplit[1], countryInfoSplit[2]);
                codes2.put(countryInfoSplit[1], countryArray);
                //countriesNames.add(countryInfoSplit[1]);
                //namesToCode.put(countryInfoSplit[0], countryInfoSplit[1]);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public String convertCode(String twoLetter) {
        return codes2.get(twoLetter)[1];
    }

    public String getCountryName(String twoLetter) {
        return codes2.get(twoLetter)[0];
    }
}
