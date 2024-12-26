package com.migration_map;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.Scanner;

public class CountryCodes {
    private Map<String, String[]> codes = new HashMap<>();
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

                String[] countryArray = {countryInfoSplit[0], countryInfoSplit[2]};

                codes.put(countryInfoSplit[1], countryArray);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public String convertCode(String twoLetter) {
        String[] arr = codes.get(twoLetter);
        return arr[1];
    }
}
