package bo.custom;

import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO{
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;
    boolean deleteItems(String code) throws SQLException, ClassNotFoundException;
    boolean saveItems(ItemDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateItems(ItemDTO dto) throws SQLException, ClassNotFoundException;
    boolean existItems(String code) throws SQLException, ClassNotFoundException;
    String generateItemNewId() throws SQLException, ClassNotFoundException;
}
