package com.migration_map;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;


public class MigrationAPI {
    private URL url;
    private String limit;
    private String yearFrom;
    private String yearTo;
    private String year;
    private String coo;
    private String coa;
    private HttpURLConnection connection;
    private final CountryCodes codes;


    public URL getUrl() {
        return url;
    }


    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(String yearFrom) {
        this.yearFrom = yearFrom;
    }

    public String getYearTo() {
        return yearTo;
    }

    public void setYearTo(String yearTo) {
        this.yearTo = yearTo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCoo() {
        return coo;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public String getCoa() {
        return coa;
    }

    public void setCoa(String coa) {
        this.coa = coa;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    MigrationAPI(CountryCodes codes) {
        this.codes = codes;
        limit = "100";
        yearFrom = "2022";
        yearTo = "2023";
        year = "2023";
        coo = "COL";
        coa = "USA";
    }

    /*
     * This method connects to the UNHCR api. Throws a RuntimeException
     * if there is an error connecting
     *
     * @param url is the URL of the UNHCR api
     * @return void
     */
    private void connect(URL url) throws Exception{
        if (connection != null) {
            connection.disconnect();
        }



        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }

    /*
     * This method sets fetches Refugee or Asylum seeker data from
     * the UNHCR url and from the chosen countries.
     *
     * @param cooTwoLetter the two-letter code of the country of origin
     * @param coaTwoLetter the two-letter code of the country of asylum
     * @param selection is the Choice of Refugees or Asylum Seekers
     * @return the number fetched from the UNHCR API
     */
    public Integer getRefugees(String cooTwoLetter, String coaTwoLetter, String selection) {
        setCoo(codes.convertCode(cooTwoLetter));
        setCoa(codes.convertCode(coaTwoLetter));

        try {
            this.url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&year=" + year
                    + "&coo=" + coo
                    + "&coa=" + coa);
            connect(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Integer output = null;

        if (selection.equals("refugees")) {
            for (UNHCRData dataI : getData(url)) {
                //System.out.println(dataI.toString());
                output = dataI.getRefugees();
            }
        } else if (selection.equals("asylum seekers")) {
            for (UNHCRData dataI : getData(url)) {
                // System.out.println(dataI.toString());
                output = dataI.getAsylumSeekers();
            }
        }

        return output;
    }

    /*
     * This method sets fetches IDP data from
     * the UNHCR url and from the chosen country.
     *
     * @param cooTwoLetter the two-letter code of the country of origin
     * @return the number fetched from the UNHCR API
     */
    public Integer getIDPs(String cooTwoLetter) {
        setCoo(codes.convertCode(cooTwoLetter));

        try {
            this.url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&year=" + year
                    + "&coo=" + coo);
            connect(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Integer output = null;
        for (UNHCRData dataI : getData(url)) {
            System.out.println(dataI.toString());
            output = dataI.getIdps();
        }

        return output;
    }



    /*
     * This method converts the retrieved JSON data from the url into
     * a class for easier access to different pieces of information
     *
     * @param url is the URL of the UNHCR api
     * @return a List of UNHCRData class each entry representing a different year
     */
    private List<UNHCRData> getData(URL url) {
        StringBuilder informationString = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }

        scanner.close();

        String info = informationString.toString();
        Gson gson = new Gson();
        UNHCRResponse data = gson.fromJson(info, UNHCRResponse.class);

        return data.getItems();
    }
}

