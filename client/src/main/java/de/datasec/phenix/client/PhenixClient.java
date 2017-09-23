package de.datasec.phenix.client;

import de.datasec.phenix.shared.packetsystem.packets.GetPacket;

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

    public <T> T get(Object key) {
        try {
            T t = (T) client.send(new GetPacket(key)).get();
            //System.out.println((client.end - client.start));
            return t;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}