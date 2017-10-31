package de.datasec.phenix.client;

import de.datasec.phenix.shared.packetsystem.packets.*;
import de.datasec.phenix.shared.util.TimeUnit;

import java.util.Collection;
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

    public <K, V> V get(K key) {
        try {
            return (V) client.sendWithFuture(new GetPacket(key)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K, V> void put(K key, V value) {
        put(key, value, true, -1, TimeUnit.MILLISECONDS);
    }

    public <K, V> void put(K key, V value, boolean overrideIfKeyExists) {
        put(key, value, overrideIfKeyExists, -1, TimeUnit.MILLISECONDS);
    }

    public <K, V> void put(K key, V value, long timeToLive, TimeUnit timeUnit) {
        put(key, value, true, timeToLive, timeUnit);
    }

    public <K, V> void put(K key, V value, boolean overrideIfKeyExists, long timeToLive, TimeUnit timeUnit) {
        if (value == null) {
            System.err.println("key cannot be null.");
            return;
        } else if (key == null) {
            System.err.println("key cannot be null.");
            return;
        }

        client.send(new PutPacket(key, value, overrideIfKeyExists, timeToLive, timeUnit));
    }

    public <K> boolean containsKey(K key) {
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

    public <V> boolean containsValue(V value) {
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

    public <K> long remove(K... keys) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) {
                System.err.printf("key at index %d cannot be null.%n", i);
            }
        }

        try {
            return (long) client.sendWithFuture(new RemovePacket(keys)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return -1L;
    }

    public <K, V> V remove(K key) {
        if (key == null) {
            System.err.println("key cannot be null.");
        }

        try {
            return (V) client.sendWithFuture(new RemovePacket(key)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K> String getTypeOfValue(K key) {
        try {
            return client.sendWithFuture(new TypeOfPacket(key)).get().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends Collection> T getValues() {
        try {
            return (T) client.sendWithFuture(new GetValuesPacket()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T extends Collection> T getKeys() {
        try {
            return (T) client.sendWithFuture(new GetKeysPacket()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K> K getRandomKey() {
        try {
            return (K) client.sendWithFuture(new RandomKeyPacket()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K, T> T rename(K oldKey, T newKey) {
        try {
            return (T) client.sendWithFuture(new RenamePacket(oldKey, newKey)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K> long getTimeToLive(K key) {
        // returns 0, if key no longer exists. -1, if key is supposed to live for ever
        try {
            return (long) client.sendWithFuture(new TimeToLivePacket(key, (byte) 1)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return -1L;
    }

    public <K> boolean setTimeToLive(K key, long timeToLive, TimeUnit timeUnit) {
        try {
            return (boolean) client.sendWithFuture(new TimeToLivePacket(key, (byte) 0, timeToLive, timeUnit)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    // TODO: Return ordered keySet and valueSet, if all of the elements have the interface comparable
}