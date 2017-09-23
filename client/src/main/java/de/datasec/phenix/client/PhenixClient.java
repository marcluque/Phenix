package de.datasec.phenix.client;

import de.datasec.phenix.shared.packetsystem.packets.GetPacket;
import de.datasec.phenix.shared.packetsystem.packets.PutPacket;
import de.datasec.phenix.shared.util.TimeUnit;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixClient {

    private Client client;

    public PhenixClient(String host, int port) {
        client = new Client(host, port);

        // Open connection
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Serializable> T get(T key) {
        try {
            return (T) client.send(new GetPacket(key)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends Serializable> void put(String key, T value) throws IllegalArgumentException {
        put(key, value, true, 0, TimeUnit.MILLISECONDS);
    }

    public <T extends Serializable> void put(String key, T value, boolean overrideIfKeyExists) throws IllegalArgumentException {
        put(key, value, overrideIfKeyExists, 0, TimeUnit.MILLISECONDS);
    }

    public <T extends Serializable> void put(String key, T value, long timeToLive, TimeUnit timeUnit) throws IllegalArgumentException {
        put(key, value, true, timeToLive, timeUnit);
    }

    public <T extends Serializable> void put(String key, T value, boolean overrideIfKeyExists, long timeToLive, TimeUnit timeUnit) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null.");
        } else if (key == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }

        client.send(new PutPacket(key, value, overrideIfKeyExists, timeToLive, timeUnit));
    }
}