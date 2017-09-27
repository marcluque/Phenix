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
        phenixClient.put(4, "aksdjkf", false);
        phenixClient.put(2, UUID.randomUUID(), false, 10000, TimeUnit.MILLISECONDS);
        phenixClient.put(3, UUID.randomUUID(), false, 9000, TimeUnit.MILLISECONDS);


        phenixClient.put();

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
