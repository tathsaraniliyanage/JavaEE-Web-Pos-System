package lk.ijse.gdse68.webpos2.util;

import java.util.UUID;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
public class Util {
    public static String idGenerate(){
        return UUID.randomUUID().toString();
    }
}
