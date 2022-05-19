package bo.custom;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;
import controller.ManageCustomersFormController;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.impl.*;

public class BOFactory {
    public static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBOFactory(){
        if (boFactory==null){
            return boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,PLACEORDER
    }

    public SuperBO getDAO(BOFactory.BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PLACEORDER:
                return new PurchaseOrderBOImpl();
                default:
                return null;
        }
    }
}
