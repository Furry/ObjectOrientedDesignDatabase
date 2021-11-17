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

    public void set(String key, String value) {
        if (size == MAX_SIZE) {
            String oldestKey = null;
            int oldestCount = 0;
            for (String key1 : referenceCounter.keySet()) {
                int count = referenceCounter.get(key1);
                if (count > oldestCount) {
                    oldestKey = key1;
                    oldestCount = count;
                }
            }
            cache.remove(oldestKey);
            referenceCounter.remove(oldestKey);
            size--;
        }
        cache.put(key, value);
        referenceCounter.put(key, 1);
        size++;
    }
}
