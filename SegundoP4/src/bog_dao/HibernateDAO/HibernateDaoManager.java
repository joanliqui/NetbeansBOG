/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.CustomerDAO;
import bog_dao.IDaoManager;
import bog_dao.OrderDAO;
import bog_dao.ProductDAO;

/**
 *
 * @author joanl
 */
public class HibernateDaoManager implements IDaoManager{

    private ProductDAO productDao = null;
    private CustomerDAO customerDao = null;
    private OrderDAO orderDao = null;

    public HibernateDaoManager() {
        productDao = new HibernateProductDao();
        customerDao = new HibernateCustomerDao();
        orderDao = new HibernateOrderDao();
    }
    
    @Override
    public ProductDAO getProductDAO() {
       return productDao;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return orderDao;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        return customerDao;
    }
    
}
