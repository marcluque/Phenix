package de.datasec.phenix.server.cache;

/**
 * Created by DataSec on 24.09.2017.
 */
public class PhenixCacheEntry<V> {

    private V value;

    private long timeToLive;

    public PhenixCacheEntry(V value, long timeToLive) {
        this.value = value;
        this.timeToLive = timeToLive;
    }

    public V getValue() {
        return value;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
