package de.datasec.phenix.server;

/**
 * Created by DataSec on 23.09.2017.
 */
public class PhenixServer {

    private int port;

    public PhenixServer(int port) {
        // Start server
        try {
            new Server(port).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
