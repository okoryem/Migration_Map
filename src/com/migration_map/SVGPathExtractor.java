package com.migration_map;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class SVGPathExtractor {

    public static Map<String, String> extractPaths(String svgFile) throws Exception {
        String svgContent = new String(Files.readAllBytes(Paths.get(svgFile)));

        Map<String, String> paths = new HashMap<>();

        // Use regex to match <path> elements with 'id' and 'd' attributes
        // "<path[^>]*\\s(id=\"([^\"]*)\")?[^>]*\\sd=\"([^\"]*)\""
        /*
        Pattern pattern = Pattern.compile(
                "<path[^>]*\\s(id=\"([^\"]*)\")?[^>]*\\sd=\"([^\"]*)\"",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );

         */

        Pattern pattern = Pattern.compile(
                "<path[^>]*\\s(id=\"([^\"]*)\")?[^>]*\\sd=\"([^\"]*)\"[^>]*>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );



        //"<path[^>]*\\bid=\"([^\"]*)\"[^>]*\\bd=\"([^\"]*)\"[^>]*>"


        Matcher matcher = pattern.matcher(svgContent);


        int counter = 0;
        while (matcher.find()) {
            String pathId = matcher.group(0);
            String id = extractID(pathId);
            String pathData = matcher.group(3).trim();

            //System.out.println(id);
            //System.out.println(pathData);
            if (id.equals(" ")) {
                id = "unknown-" + counter++;
            }
            paths.put(id, pathData);
        }
        System.out.println(counter);


        //paths.forEach((key, value) -> System.out.println("ID: " + key + ", Path: " + value));
        //paths.forEach((key, value) -> System.out.println("ID: " + key));
        //System.out.println(paths.get("AF"));

        /*
        for (String id : paths.keySet()) {
            if (!id.startsWith("unknown")) {
                System.out.println(id);
            }
        }

         */

        return paths;
    }

    private static String extractID(String input) {
        Pattern pattern = Pattern.compile("id=\"(.*?)\"");
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            return matcher.group(1);
        }
        return " ";
    }
}
