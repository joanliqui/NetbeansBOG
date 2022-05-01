package bog_dao;

public interface IDaoManager {

    ProductDAO getProductDAO();

    OrderDAO getOrderDAO();

    CustomerDAO getCustomerDAO();
    
}
