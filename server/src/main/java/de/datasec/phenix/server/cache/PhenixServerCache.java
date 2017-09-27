package de.datasec.phenix.server.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by DataSec on 24.09.2017.
 */
public class PhenixServerCache<K, V> {

    private Map<K, PhenixCacheEntry<V>> cache = new HashMap<>();

    private Set<K> toBeRemoved = new HashSet<>();

    public PhenixServerCache(int cleanUpRate) {
        cleanUpRate = cleanUpRate > 0 ? cleanUpRate : 120;
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::startCleaner, cleanUpRate, cleanUpRate, TimeUnit.SECONDS);
    }

    private void startCleaner() {
        cache.forEach((key, value) -> {
            if (value.getTimeToLive() != -1 && value.getTimeToLive() <= System.currentTimeMillis()) {
                toBeRemoved.add(key);
            }
        });
        toBeRemoved.forEach(cache::remove);
        toBeRemoved.clear();
    }

    public void put(K key, V value, boolean overrideIfKeyExists, long timeToLive) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        timeToLive = timeToLive >= 0 ? timeToLive + System.currentTimeMillis() : -1;

        if (!overrideIfKeyExists) {
            if (!cache.containsKey(key)) {
                cache.put(key, new PhenixCacheEntry<>(value, timeToLive));
            }
        } else {
            cache.put(key, new PhenixCacheEntry<>(value, timeToLive));
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public boolean containsValue(V value) {
        final boolean[] containsValue = {false};
        cache.values().forEach(entry -> {
            if (entry.getValue().equals(value)) {
                containsValue[0] = true;
            }
        });

        return containsValue[0];
    }

    public boolean remove(K key) {
        return getAndRemove(key) != null;
    }

    public V getAndRemove(K key) {
        return get(key) != null ? cache.remove(key).getValue() : null;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        PhenixCacheEntry<V> entry = cache.get(key);

        if (entry == null) {
            throw new IllegalArgumentException("cache entry cannot be null");
        }

        long timeToLive = entry.getTimeToLive();
        if (timeToLive != -1 && timeToLive <= System.currentTimeMillis()) {
            remove(key);
            return null;
        }

        return cache.get(key).getValue();
    }

    public Set<K> getKeys() {
        Set<K> set = new HashSet<>();
        set.addAll(cache.keySet());
        return set;
    }

    public boolean setTimeToLive(K key, long timeToLive) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        PhenixCacheEntry<V> entry = cache.get(key);

        if (entry != null) {
            entry.setTimeToLive(timeToLive >= 0 ? timeToLive + System.currentTimeMillis() : -1);
            return true;
        } else {
            return false;
        }
    }

    public long getTimeToLive(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        PhenixCacheEntry<V> entry = cache.get(key);

        if (entry == null) {
            return 0;
        }

        long timeToLive = entry.getTimeToLive();

        return timeToLive >= 0 ? timeToLive - System.currentTimeMillis() : -1;
    }

    public V rename(K oldKey, K newKey) {
        PhenixCacheEntry entry = cache.remove(oldKey);
        cache.put(newKey, entry);
        return (V) entry.getValue();
    }
}