package de.datasec.phenix.client;

import de.datasec.phenix.shared.packetsystem.packets.GetPacket;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixClient {

    private String host;

    private int port;

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

    public <T> T get(Object key) {
        // TODO: CREATE ID
        Future<Object> future = client.send(new GetPacket(0, key));

        try {
            return (T) future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
