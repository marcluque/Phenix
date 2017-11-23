package de.datasec.phenix.server;

/**
 * Created by DataSec on 23.11.2017.
 */
public class ExampleServer {

    public static void main(String[] args) {
        // Creates a new Phenix server instance on the given host. The port as parameter is obligatory
        // Not obligatory are the host and cleanup rate. Standard value for the cleanup rate is 120.
        // The standard host is localhost, but any host could be specified here
        PhenixServer phenixServer = new PhenixServer("localhost", 8888, 60);

        // This is an example for the use of just the port and a custom cleanUpRate
        //PhenixServer phenixServer1 = new PhenixServer(8888, 60);
    }
}
