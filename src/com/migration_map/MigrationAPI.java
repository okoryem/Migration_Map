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
    private String coo = "COL";
    private String coa = "USA";
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

        /*
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

         */

    }


    private void connect() throws Exception{
        if (connection != null) {
            connection.disconnect();
        }

         this.url = new URL("https://api.unhcr.org/population/v1/population/?"
                + "limit=" + limit
                + "&yearFrom=" + yearFrom
                + "&yearTo=" + yearTo
                + "&coo=" + coo
                + "&coa=" + coa);


        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }


    public Integer getRefugees(String cooTwoLetter, String coaTwoLetter) {
        String convertedCoo = codes.convertCode(cooTwoLetter);
        String convertedCoa = codes.convertCode(coaTwoLetter);
        setUrlParameters(convertedCoo, convertedCoa);

        try {
            connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder informationString = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Integer output = null;

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }


        scanner.close();

        String info = informationString.toString();
        System.out.println(info);

        /*
        #######
        #######
        Error somehwere in here
        #######
        #######
         */

        Gson gson = new Gson();
        UNHCRResponse data = gson.fromJson(info, UNHCRResponse.class);

        List<UNHCRData> dataU = data.getItems();

        for (UNHCRData dataI : dataU) {
            System.out.println(dataI.toString());
            output = dataI.getRefugees();
        }

        return output;

        /*
        #######
        #######
        #######
        #######
         */
    }

    private void setUrlParameters(String newCoo, String newCoa) {
        /*
        try {
            URL url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&yearFrom=" + yearFrom
                    + "&yearTo=" + yearTo
                    + "&coo=" + newCoo
                    + "&coa" + newCoa);

            connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

         */
        this.coo = newCoo;
        this.coa = newCoa;

    }
}

