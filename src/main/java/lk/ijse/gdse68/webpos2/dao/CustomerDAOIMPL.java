package lk.ijse.gdse68.webpos2.dao;

import lk.ijse.gdse68.webpos2.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */
public class CustomerDAOIMPL implements CustomerDAO{
    public static String SAVE_CUSTOMER = "INSERT INTO customer (id,username,fullName,street,lane,city,email) VALUES(?,?,?,?,?,?,?)";
    public static String GET_CUSTOMER = "SELECT * FROM customer WHERE id=?";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET username=?,fullName=?,street=?,lane=?,city=?,email=? WHERE id=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE id=?";
    public static String GET_ALL_CUSTOMER = "SELECT * FROM customer";


    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getUsername());
            ps.setString(3, customer.getFullName());
            ps.setString(4, customer.getStreet());
            ps.setString(5, customer.getLane());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getEmail());
            if(ps.executeUpdate() != 0){
                return "Customer Save Successfully";
            }else {
                return "Failed to Save Student";
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMER);
            System.out.println(id);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }


    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(7, customer.getId());
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getStreet());
            ps.setString(4, customer.getLane());
            ps.setString(5, customer.getCity());
            ps.setString(6, customer.getEmail());
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        try {
            CustomerDTO customerDTO = new CustomerDTO();
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()){
                customerDTO.setId(rst.getString("id"));
                customerDTO.setUsername(rst.getString("username"));
                customerDTO.setFullName(rst.getString("fullName"));
                customerDTO.setStreet(rst.getString("street"));
                customerDTO.setCity(rst.getString("city"));
                customerDTO.setLane(rst.getString("lane"));
                customerDTO.setEmail(rst.getString("email"));
            }
            return  customerDTO;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMER);
            ResultSet resultSet = statement.executeQuery();

            List<CustomerDTO> list=new ArrayList<>();
            while (resultSet.next()){
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setId(resultSet.getString("id"));
                customerDTO.setUsername(resultSet.getString("username"));
                customerDTO.setFullName(resultSet.getString("fullName"));
                customerDTO.setStreet(resultSet.getString("street"));
                customerDTO.setCity(resultSet.getString("city"));
                customerDTO.setLane(resultSet.getString("lane"));
                customerDTO.setEmail(resultSet.getString("email"));
                list.add(customerDTO);
            }
            return  list;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
