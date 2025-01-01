package com.migration_map;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class SVGPathExtractor {

    public static Map<String, String> extractPaths(String svgFile) throws Exception {
        String svgContent = new String(Files.readAllBytes(Paths.get(svgFile)));

        Map<String, String> paths = new HashMap<>();


        Pattern pattern = Pattern.compile(
                "<path[^>]*\\s(id=\"([^\"]*)\")?[^>]*\\sd=\"([^\"]*)\"[^>]*>",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );



        Matcher matcher = pattern.matcher(svgContent);


        int counter = 0;
        while (matcher.find()) {
            String pathId = matcher.group(0);
            String id = extractID(pathId);
            String title = extractTitle(pathId);
            String pathData = matcher.group(3).trim();

            if (id.equals(" ")) {
                id = "unknown-" + counter++;
            }
            paths.put(id, pathData);
        }

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

    private static String extractTitle(String input) {
        Pattern pattern = Pattern.compile("title=\"(.*?)\"");
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            return matcher.group(1);
        }
        return " ";
    }
}
