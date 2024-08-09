package lk.ijse.gdse68.webpos2.bo.impl;

import lk.ijse.gdse68.webpos2.bo.ItemBo;
import lk.ijse.gdse68.webpos2.dao.ItemDAO;
import lk.ijse.gdse68.webpos2.dao.impl.ItemDAOImpl;
import lk.ijse.gdse68.webpos2.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Praboda Thathsarani
 * @Project webpos2
 */

public class ItemBOImpl implements ItemBo {
    private final ItemDAO itemDAO =new ItemDAOImpl();
    @Override
    public String saveItem(ItemDTO itemDTO, Connection connection) throws Exception {
        return itemDAO.saveItem(itemDTO, connection);
    }

    @Override
    public boolean deleteItem(String code, Connection connection) throws Exception {
        return itemDAO.deleteItem(code, connection);
    }

    @Override
    public boolean updateItem(String code, ItemDTO itemDTO, Connection connection) throws Exception {
        return itemDAO.updateItem(code, itemDTO, connection);
    }

    @Override
    public ItemDTO getItem(String code, Connection connection) throws Exception {
        return itemDAO.getItem(code, connection);
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        return itemDAO.getAllItems(connection);
    }
}
