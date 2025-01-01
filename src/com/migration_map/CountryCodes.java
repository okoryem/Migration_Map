package com.migration_map;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class CountryCodes {
    //private Map<String, String[]> codes = new HashMap<>();
    private Map<String, String> codes = new HashMap<>();
    private Map<String, String[]> codes2 = new HashMap<>();
    private final File csvFile = new File("all.csv");

    CountryCodes() {
        loadFile();
    }

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
                //String[] countryArray = {countryInfoSplit[0], countryInfoSplit[2]};


                //codes.put(countryInfoSplit[1], countryArray);
                codes.put(countryInfoSplit[1], countryInfoSplit[2]);
                codes2.put(countryInfoSplit[1], countryArray);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public String convertCode(String twoLetter) {
        //String[] arr = codes.get(twoLetter);
        //return arr[1];
        return codes2.get(twoLetter)[1];
    }

    public String getCountryName(String twoLetter) {
        return codes2.get(twoLetter)[0];
    }
}
