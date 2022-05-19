package dao.custom;

import dao.CrudDAO;
import dao.SuperDAO;
import model.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomDTO> searchOrderByOrderId(String id)throws SQLException,ClassNotFoundException;
}
