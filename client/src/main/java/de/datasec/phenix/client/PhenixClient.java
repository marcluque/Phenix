package de.datasec.phenix.client;

import de.datasec.phenix.client.network.Client;
import de.datasec.phenix.shared.network.packetsystem.packets.GetPacket;

/**
 * Created by DataSec on 05.09.2017.
 */
public class PhenixClient {

    private String host;

    private int port;

    private Client client;

    public PhenixClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        // Open connection
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <V> V get(Object key) {
        // TODO: CREATE ID
        // TODO: HANDLE RESPONSE OF send() WITH FUTURE
        client.send(new GetPacket(0, key));

        return null;
    }
}
