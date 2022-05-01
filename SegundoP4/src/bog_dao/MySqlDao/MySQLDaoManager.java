package bog_dao.MySqlDao;

import bog_dao.CustomerDAO;
import bog_dao.DAOOrderFactory;
import bog_dao.IDaoManager;
import bog_dao.OrderDAO;
import bog_dao.ProductDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDaoManager implements IDaoManager {
    private Connection con;

    private ProductDAO productDao = null;
    private CustomerDAO customerDao = null;
    private OrderDAO orderDao = null;

    private MySQLDaoProductFactory daoProductFactory;
    private MySQLDaoCustomerFactory daoCustomerFactory;
    private DAOOrderFactory daoOrderFactory; 


    public MySQLDaoManager() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/P5_Prod3_BOG", "bog", "bog");
        daoProductFactory = new MySQLDaoProductFactory();
        daoCustomerFactory = new MySQLDaoCustomerFactory();
        daoOrderFactory = new MySQLDaoOrderFactory();
    }


    @Override
    public ProductDAO getProductDAO() {
        if(productDao == null){
            productDao = daoProductFactory.createDAO(con);
        }
        return productDao;
    }

    @Override
    public OrderDAO getOrderDAO() {
        if(orderDao == null){
            orderDao = daoOrderFactory.createDAO(con);
        }
        return orderDao;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        if(customerDao == null ){
            customerDao = daoCustomerFactory.createDAO(con);
        }
        return customerDao;
    }

}
