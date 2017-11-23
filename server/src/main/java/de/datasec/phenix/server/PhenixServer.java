package de.datasec.phenix.server;

/**
 * Created by DataSec on 23.09.2017.
 */
public class PhenixServer {

    public PhenixServer(int port) {
        this(port,120);
    }

    public PhenixServer(int port, int cleanUpRate) {
        try {
            new Server(port, cleanUpRate).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PhenixServer(String host, int port) {
        this(host, port, 120);
    }

    public PhenixServer(String host, int port, int cleanUpRate) {
        try {
            new Server(host, port, cleanUpRate).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}