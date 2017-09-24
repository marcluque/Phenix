package de.datasec.phenix.server;

/**
 * Created by DataSec on 23.09.2017.
 */
public class PhenixServer {

    public PhenixServer(int port) {
        this(port,120);
    }

    public PhenixServer(int port, int cleanUpRate) {
        // Start server
        try {
            new Server(port, cleanUpRate).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
