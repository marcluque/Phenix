import de.datasec.phenix.client.PhenixClient;
import de.datasec.phenix.shared.util.TimeUnit;

import java.util.UUID;

/**
 * Created by DataSec on 23.11.2017.
 */
public class ExampleClient {

    public static void main(String[] args) {
        PhenixClient phenixClient = new PhenixClient("localhost", 8888);

        UUID inputUuid = UUID.randomUUID();
        // By default when a key already exists it's overridden and the time to live set to infinite, which could be set
        // manually by setting the time to live to -1
        phenixClient.put("key_1", inputUuid);

        // Overrides, if the given key already exists
        phenixClient.put("key_1", UUID.randomUUID(), true);

        // Puts a key with a given time to live and it's unit
        phenixClient.put("key_2", UUID.randomUUID(), 24, TimeUnit.HOURS);

        // In this case the key_2 is not overridden, as the boolean is false
        // Even though this method takes every possible option
        phenixClient.put("key_2", UUID.randomUUID(), false, 24, TimeUnit.MINUTES);

        // Save the returned value that corresponds to the given key (key_1)
        UUID outputUuid = phenixClient.get("key_1");
        // Outputs the uuid that was put into the cache with key_1
        System.out.println(outputUuid);
        System.out.println(outputUuid.compareTo(inputUuid));

        //
        boolean containsKey = phenixClient.containsKey("key_1");
        System.out.println(containsKey);
    }
}
