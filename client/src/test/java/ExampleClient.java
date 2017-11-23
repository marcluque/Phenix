import de.datasec.phenix.client.PhenixClient;
import de.datasec.phenix.shared.util.TimeUnit;

import java.util.Set;
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
        phenixClient.put("key_1", UUID.randomUUID());

        // Overrides, if the given key already exists
        phenixClient.put("key_1", inputUuid, true);

        // Puts a key with a given time to live and it's unit
        phenixClient.put("key_2", UUID.randomUUID(), 24, TimeUnit.HOURS);

        // In this case the key_2 is not overridden, as the boolean is false
        // Even though this method takes every possible option
        phenixClient.put("key_2", UUID.randomUUID(), false, 24, TimeUnit.MINUTES);


        // Save the returned value that corresponds to the given key (key_1)
        UUID outputUuid = phenixClient.get("key_1");
        // Outputs the uuid that was put into the cache with key_1
        System.out.println("\nOutput: " + outputUuid);
        System.out.println("UUID equal: " + (outputUuid.compareTo(inputUuid) == 0));


        // Checks if the cache contains the given key and returns a boolean
        boolean containsKey = phenixClient.containsKey("key_1");
        // Verify that the returned uuid actually exists in the cache for presentation purpose
        boolean containsValue = phenixClient.containsValue(outputUuid);
        System.out.println("\nKey exists: " + containsKey + " | Value exists: " + containsValue);


        // Returns the type of the corresponding value to the key
        // The type is returned as String
        String typeOfValue = phenixClient.getTypeOfValue("key_1");
        System.out.println("\nType of value: " + typeOfValue);


        // This method returns all the values stored in the cache. It has be saved as Set or any sub type of Set
        Set<UUID> uuids = phenixClient.getValues();
        System.out.println("\n-----VALUES-----");
        uuids.forEach(System.out::println);
        System.out.println("---VALUES-END---");


        // This method returns all the keys stored in the cache. It has be saved as Set or any sub type of Set
        Set<String> stack = phenixClient.getKeys();
        System.out.println("\n-----KEYS-----");
        stack.forEach(System.out::println);
        System.out.println("---KEYS-END---");


        // Returns a random key from the cache
        String randomKey = phenixClient.getRandomKey();
        System.out.println("\nRandom key: " + randomKey);
        // Outputs the value that corresponds to the random key that was returned by getRandomKey()
        System.out.println("Value corresponding to random key: " + phenixClient.get(randomKey));


        // Changes the keys in the cache, but the types have to be the same
        // The first parameter is the key that should be changed, the second one the new key
        // The method returns the value that both of the keys would correspond to
        UUID valueOfRenamedKeys = phenixClient.rename("key_1", "key_3");
        System.out.println("\nValue of keys that were renamed: " + valueOfRenamedKeys);


        // Sets the time to live of a specified key and its value
        // Returns whether the time could be set or not. If it couldn't be set it's quite likely that the
        // key doesn't exist anymore
        boolean ttlChanged = phenixClient.setTimeToLive("key_2", 15, TimeUnit.SECONDS);
        System.out.println("\nWas time to live changed?: " + ttlChanged);

        // This method returns the time that is left to live for the object
        // It returns the amount of time in milliseconds
        // -1 in this case stands for inanity
        long timeToLive = phenixClient.getTimeToLive("key_2");
        System.out.println("Time to live: " + timeToLive + " ms");


        // Remove the value stored with the given together. Method returns the removed value, if it exists,
        // otherwise it returns null
        UUID deletedUuid = phenixClient.remove("key_3");
        // Outputs the uuid that was put into the cache with key_1
        System.out.println("\nDeleted UUID: " + deletedUuid);
        System.out.println("UUID equal: " + (deletedUuid.compareTo(outputUuid) == 0));

        //
        long amountNotRemoved = phenixClient.remove("key_1", "key_2");
        System.out.println("\nAmount not removed: " + amountNotRemoved);
    }
}