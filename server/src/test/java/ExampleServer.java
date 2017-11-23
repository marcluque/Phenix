import de.datasec.phenix.server.PhenixServer;

/**
 * Created by DataSec on 23.11.2017.
 */
public class ExampleServer {

    public static void main(String[] args) {
        //
        PhenixServer phenixServer = new PhenixServer("localhost", 8888, 60);

        //
        //PhenixServer phenixServer1 = new PhenixServer(8888, 60);
    }
}
