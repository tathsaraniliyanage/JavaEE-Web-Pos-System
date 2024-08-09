package lk.ijse.gdse68.webpos2.bo;

import lk.ijse.gdse68.webpos2.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
public interface CustomerBO {
    String saveCustomer(CustomerDTO customer, Connection connection)throws Exception;
    boolean deleteCustomer(String id, Connection connection)throws Exception;
    boolean updateCustomer(String id,CustomerDTO customer,Connection connection)throws Exception;
    CustomerDTO getCustomer(String id, Connection connection)throws Exception;
    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
}
