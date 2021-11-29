package libs;

import java.util.Map;

/**
 * This class converts a query string into a map of key-value pairs using iteration.
 * This has literally no other use, but I feel if I didn't make a seperate class for this, I'd have
 * violated the Single Responsibility Principle.
 */
public class QueryString {
    /**
     * Converts a query string into a map of key-value pairs.
     * @param queryString The query string to convert.
     * @return The resulting map of values.
     */
    public static Map<String, String> of(String queryString) {

        // Create an empty map
        Map<String, String> map = new java.util.HashMap<>();

        // Split by & separators
        String[] keyValuePairs = queryString.split("&");

        // Iterate over each one and add to the map.
        for (String keyValuePair : keyValuePairs) {
            String[] keyAndValue = keyValuePair.split("=");
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            map.put(key, value);
        }

        // Return the map.
        return map;
    }
}
