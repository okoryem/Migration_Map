package com.migration_map;




import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;


public class MigrationAPI {
    private URL url;
    private String limit = "100";
    private String yearFrom = "1990";
    private String yearTo = "2000";
    private  String year = "2023";
    private String coo = "COL";
    private String coa = "USA";
    private HttpURLConnection connection;
    private CountryCodes codes = new CountryCodes();

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

    MigrationAPI() {
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        try {
            //url = new URL("https://api.unhcr.org/population/v1/population/?limit=10&yearFrom=2014&yearTo=2023");
            //url = new URL("https://api.unhcr.org/population/v1/unrwa/?page=56&limit=56&yearFrom=56&yearTo=56&year=&download=true&coo=coo_example&coa=coa_example&coo_all=true&coa_all=true&cf_type=cfType_example");
            /*
            url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&yearFrom=" + yearFrom
                    + "&yearTo=" + yearTo
                    + "&coo=" + coo
                    + "&coa" + coa);

             */

            /*
            url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&year=" + year
                    + "&coo=" + coo
                    + "&coa" + coa);


            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();


            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);


            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }

                scanner.close();

                System.out.println(informationString + "\n");
                String info = informationString.toString();

                Gson gson = new Gson();
                UNHCRResponse data = gson.fromJson(info, UNHCRResponse.class);

                List<UNHCRData> dataU = data.getItems();

                for (UNHCRData dataI : dataU) {
                    System.out.println("Refugees:" + dataI.getRefugees());
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


             */


    }


    private void connect() throws Exception{
        url = new URL("https://api.unhcr.org/population/v1/population/?"
                + "limit=" + limit
                + "&year=" + year
                + "&coo=" + coo
                + "&coa" + coa);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();


        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        }
    }


    public String getRefugees(String newYear,String cooTwoLetter, String coaTwoLetter) {
        String convertedCoo = codes.convertCode(cooTwoLetter);
        String convertedCoa = codes.convertCode(coaTwoLetter);
        setUrlParameters(newYear, convertedCoo, convertedCoa);

        StringBuilder informationString = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String output = "";

        while (scanner.hasNext()) {
            informationString.append(scanner.nextLine());
        }

        scanner.close();

        String info = informationString.toString();

        Gson gson = new Gson();
        UNHCRResponse data = gson.fromJson(info, UNHCRResponse.class);

        List<UNHCRData> dataU = data.getItems();


        for (UNHCRData dataI : dataU) {
            output = "Refugees:" + dataI.getRefugees();
        }

        return output;
    }

    private void setUrlParameters(String newYear, String newCoo, String newCoa) {
        try {
            url = new URL("https://api.unhcr.org/population/v1/population/?"
                    + "limit=" + limit
                    + "&year=" + newYear
                    + "&coo=" + newCoo
                    + "&coa" + newCoa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

