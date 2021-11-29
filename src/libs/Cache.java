package libs;

import java.util.HashMap;

/**
 * The Cache Class is a simple-ish structure that stores a HashMap of frequently queried entries,
 * as well as a count of how many times each entry has been queried. Using this data, it determines
 * the top x (100 by default) most referenced entries.
 *
 * When the database is queried for a specific value, the cache is checked first. If the value is
 * found in the cache, the value is returned. If the value is not found in the cache, the database
 * searches the database file instead.
 *
 * This implementation is a Singleton Pattern, meaning that there is only one instance of the Cache at any
 * given time.
 */
public class Cache {
    public static final int MAX_SIZE = 100;
    private int size;
    private HashMap<String, String> cache;
    private HashMap<String, Integer> referenceCounter;

    /**
     * Constructor for the Cache Class.
     */
    public Cache() {
        size = 0;

        // Insure the cache doesn't get above the max size.
        cache = new HashMap<String, String>(MAX_SIZE);
        referenceCounter = new HashMap<String, Integer>();
    }

    /**
     * Returns the max size of the cache.
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets a key from the cache, returning null if the key is not found.
     * @param key The key to get from the cache.
     * @return The value of the key, or null if the key is not found.
     */
    public String get(String key) {
        if (cache.containsKey(key)) {
            int count = referenceCounter.get(key);
            referenceCounter.put(key, count + 1);
            return cache.get(key);
        } else {
            return null;
        }
    }

    /**
     * Adds a new key/value pair to the cache, removing the least referenced key if the cache is full.
     * @param key The key to add to the cache.
     * @param value The value they key should have in the cache.
     */
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
