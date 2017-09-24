import de.datasec.phenix.client.PhenixClient;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by DataSec on 23.09.2017.
 */
public class MainClient {

    public static void main(String[] args) {
        PhenixClient phenixClient = new PhenixClient("localhost", 8080);
        //phenixClient.put(1, "HAY", false);
        //phenixClient.put(2, "HAY", false);
        HashSet<Integer> values = new HashSet<>();
        values.add(1);
        values.add(2);
        values.add(3);
        ArrayList<Integer> list = new ArrayList<>();
        phenixClient.put("list2", list);
        HashSet<Integer> set = phenixClient.get("list1");
        System.out.println(set.toString());
    }
}
