package libs;

import java.util.HashMap;

public class Cache {
    public static final int MAX_SIZE = 100;
    private int size;
    private HashMap<String, String> cache;
    private HashMap<String, Integer> referenceCounter;

    public Cache() {
        size = 0;
        cache = new HashMap<String, String>(MAX_SIZE);
        referenceCounter = new HashMap<String, Integer>();
    }

    public int getSize() {
        return size;
    }

    public String get(String key) {
        if (cache.containsKey(key)) {
            int count = referenceCounter.get(key);
            referenceCounter.put(key, count + 1);
            return cache.get(key);
        } else {
            return null;
        }
    }
}
