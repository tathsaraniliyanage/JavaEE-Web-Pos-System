package lk.ijse.gdse68.webpos2.dao.impl;

import lk.ijse.gdse68.webpos2.dao.ItemDAO;
import lk.ijse.gdse68.webpos2.dto.CustomerDTO;
import lk.ijse.gdse68.webpos2.dto.ItemDTO;

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

public class ItemDAOImpl implements ItemDAO {
    public static String SAVE_Item = "INSERT INTO item (code,item_name,qty,price,description,image) VALUES(?,?,?,?,?,?)";
    public static String GET_Item = "SELECT * FROM item WHERE code=?";
    public static String UPDATE_Item = "UPDATE item SET item_name=?,qty=?,price=?,description=?,image=? WHERE code=?";
    public static String DELETE_Item = "DELETE FROM item WHERE code=?";
    public static String GET_ALL_Item = "SELECT * FROM item";

    @Override
    public String saveItem(ItemDTO itemDTO, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(SAVE_Item);
            ps.setString(1, itemDTO.getCode());
            ps.setString(2, itemDTO.getItem_name());
            ps.setDouble(3, itemDTO.getQty());
            ps.setDouble(4, itemDTO.getPrice());
            ps.setString(5, itemDTO.getDescription());
            ps.setString(6, itemDTO.getImage());
            if(ps.executeUpdate() != 0){
                return "Item Save Successfully";
            }else {
                return "Failed to Save Item";
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteItem(String code, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(DELETE_Item);
            System.out.println(code);
            ps.setString(1, code);
            return ps.executeUpdate() != 0;
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }

    }

    @Override
    public boolean updateItem(String code, ItemDTO itemDTO, Connection connection) throws Exception {
        try {
            var ps = connection.prepareStatement(UPDATE_Item);
            ps.setString(6, itemDTO.getCode());
            ps.setString(1, itemDTO.getItem_name());
            ps.setDouble(2, itemDTO.getQty());
            ps.setDouble(3, itemDTO.getPrice());
            ps.setString(4, itemDTO.getDescription());
            ps.setString(5, itemDTO.getImage());

            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public ItemDTO getItem(String code, Connection connection) throws Exception {
        try {
            ItemDTO itemDTO = new ItemDTO();
            var ps = connection.prepareStatement(GET_Item);
            ps.setString(1, code);
            var rst = ps.executeQuery();
            while (rst.next()){
                itemDTO.setCode(rst.getString(1));
                itemDTO.setItem_name(rst.getString(2));
                itemDTO.setQty(rst.getDouble(3));
                itemDTO.setPrice(rst.getDouble(4));
                itemDTO.setDescription(rst.getString(5));
                itemDTO.setImage(rst.getString(6));
            }
            return  itemDTO;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_Item);
            ResultSet resultSet = statement.executeQuery();

            List<ItemDTO> list=new ArrayList<>();
            while (resultSet.next()){
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setCode(resultSet.getString(1));
                itemDTO.setItem_name(resultSet.getString(2));
                itemDTO.setQty(resultSet.getDouble(3));
                itemDTO.setPrice(resultSet.getDouble(4));
                itemDTO.setDescription(resultSet.getString(5));
                itemDTO.setImage(resultSet.getString(6));
                list.add(itemDTO);
            }
            return  list;
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
