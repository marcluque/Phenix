import de.datasec.phenix.client.PhenixClient;

/**
 * Created by DataSec on 23.09.2017.
 */
public class MainClient {

    public static void main(String[] args) {
        PhenixClient phenixClient = new PhenixClient("localhost", 8080);
        System.out.println(phenixClient.get("1").toString());
    }
}
