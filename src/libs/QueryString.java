package libs;

import java.util.Map;

/**
 * This class converts a query string into a map of key-value pairs using iteration.
 */
public class QueryString {
    public static Map<String, String> of(String queryString) {
        Map<String, String> map = new java.util.HashMap<>();
        String[] keyValuePairs = queryString.split("&");
        for (String keyValuePair : keyValuePairs) {
            String[] keyAndValue = keyValuePair.split("=");
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            map.put(key, value);
        }
        return map;
    }
}
