package de.datasec.phenix.client;

import de.datasec.phenix.shared.packetsystem.packets.ContainsPacket;
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

    public <K, V extends Serializable> V get(K key) {
        try {
            return (V) client.sendWithFuture(new GetPacket(key)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K, V extends Serializable> void put(K key, V value) {
        put(key, value, true, -1, TimeUnit.MILLISECONDS);
    }

    public <K extends Serializable, V extends Serializable> void put(K key, V value, boolean overrideIfKeyExists) {
        put(key, value, overrideIfKeyExists, -1, TimeUnit.MILLISECONDS);
    }

    public <K, V extends Serializable> void put(K key, V value, long timeToLive, TimeUnit timeUnit) {
        put(key, value, true, timeToLive, timeUnit);
    }

    public <K, V extends Serializable> void put(K key, V value, boolean overrideIfKeyExists, long timeToLive, TimeUnit timeUnit) {
        if (value == null) {
            System.err.println("key cannot be null.");
            return;
        } else if (key == null) {
            System.err.println("key cannot be null.");
            return;
        }

        client.send(new PutPacket(key, value, overrideIfKeyExists, timeToLive, timeUnit));
    }

    public <T extends Serializable> boolean containsKey(T key) {
        if (key == null) {
            System.err.println("key cannot be null.");
            return false;
        }

        try {
            return (boolean) client.sendWithFuture(new ContainsPacket(key, (byte) 1)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public <T extends Serializable> boolean containsValue(T value) {
        if (value == null) {
            System.err.println("key cannot be null.");
            return false;
        }

        try {
            return (boolean) client.sendWithFuture(new ContainsPacket(value, (byte) 0)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }
}