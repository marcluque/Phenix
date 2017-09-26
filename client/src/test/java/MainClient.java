import de.datasec.phenix.client.PhenixClient;

import java.util.UUID;

/**
 * Created by DataSec on 23.09.2017.
 */
public class MainClient {

    public static void main(String[] args) {
        PhenixClient phenixClient = new PhenixClient("localhost", 8080);
        phenixClient.put(1, "HAY", false);
        phenixClient.put(2, UUID.randomUUID(), false);

        for (Object o : phenixClient.getKeys()) {
            System.out.println(o);
        }

        //System.out.println(phenixClient.getTypeOfValue(1));
        //System.out.println(phenixClient.getTypeOfValue(2));
        //System.out.println(phenixClient.get(1).toString());
        //System.out.println(phenixClient.get(2).toString());
        /*HashSet<Integer> values = new HashSet<>();
        values.add(1);
        values.add(2);
        values.add(3);
        ArrayList<Integer> list = new ArrayList<>();
        phenixClient.put("list1", values);*/
        //HashSet<Integer> set = phenixClient.get("list1");
    }
}
