package lk.ijse.gdse68.webpos2.bo;

import lk.ijse.gdse68.webpos2.dao.CustomerDAO;
import lk.ijse.gdse68.webpos2.dao.CustomerDAOIMPL;
import lk.ijse.gdse68.webpos2.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
public class CustomerBOIMPL implements CustomerBO{
    private CustomerDAO customerDAOIMPL = new CustomerDAOIMPL();
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOIMPL();
        return customerDAOIMPL.saveCustomer(customer, connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOIMPL();
        return customerDAOIMPL.deleteCustomer(id, connection);
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOIMPL();
        return customerDAOIMPL.updateCustomer(id, customer, connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {

        return customerDAOIMPL.getCustomer(id, connection);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        return customerDAOIMPL.getAllCustomers(connection);
    }
}
