package lk.ijse.gdse68.webpos2.bo;

import lk.ijse.gdse68.webpos2.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */

public interface ItemBo {
    String saveItem(ItemDTO customer, Connection connection)throws Exception;
    boolean deleteItem(String code, Connection connection)throws Exception;
    boolean updateItem(String code,ItemDTO customer,Connection connection)throws Exception;
    ItemDTO getItem(String code, Connection connection)throws Exception;
    List<ItemDTO> getAllItems(Connection connection) throws SQLException;
}
