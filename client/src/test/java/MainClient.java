import de.datasec.phenix.client.PhenixClient;
import de.datasec.phenix.shared.util.TimeUnit;

import java.util.UUID;

/**
 * Created by DataSec on 23.09.2017.
 */
public class MainClient {

    public static void main(String[] args) {
        PhenixClient phenixClient = new PhenixClient("localhost", 8080);
        phenixClient.put(1, "HAY", false);
        phenixClient.put(4, "asdf", false);
        phenixClient.put(2, UUID.randomUUID(), false, 10000, TimeUnit.MILLISECONDS);
        phenixClient.put(3, UUID.randomUUID(), false, 9000, TimeUnit.MILLISECONDS);
    }
}