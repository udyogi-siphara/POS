package bo;

import dao.custom.*;
import dao.custom.impl.*;
import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBO{
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final ItemDAO itemDAO = new ItemDAOImpl();
    private final ItemDAO orderDAO = new ItemDAOImpl();
    private final OrderDetailDAO orderDetailsDAO = new OrderDetailDAOImpl();
    private final OrderDAO orderDAO1 = new OrderDAOImpl();
    private final QueryDAO queryDAO = new QueryDAOImpl();

    @Override
    public boolean purchaseOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = DBConnection.getDbConnection().getConnection();

        orderDAO.exist(orderId);
        if ( orderDAO.exist(orderId)) {

        }

        connection.setAutoCommit(false);
        boolean save = orderDAO1.save(new OrderDTO(orderId,orderDate,customerId));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        for (OrderDetailDTO detail : orderDetails) {
            boolean save1 =  orderDetailsDAO.save(detail);
            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

//                //Search & Update Item
//                    ItemDTO item = findItem(detail.getItemCode());
            ItemDTO item = null;
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());


            boolean update = itemDAO.update(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }
    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }
    @Override
    public ItemDTO searchItem(String code) throws SQLException,ClassNotFoundException{
        return itemDAO.search(code);
    }
    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }
    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }
    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewId();
    }
    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        return customerDAO.getAll();
    }
    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {
        return itemDAO.getAll();
    }
}
