/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.CustomerDAO;
import bog_dao.DAOCustomerFactory;
import java.sql.Connection;

/**
 *
 * @author joanl
 */
public class HibernateDaoCustomerFactory implements DAOCustomerFactory{

    @Override
    public CustomerDAO createDAO(Connection con) {
        return new HibernateCustomerDao();
    }
    
}
