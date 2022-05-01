/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.DAOOrderFactory;
import bog_dao.OrderDAO;
import java.sql.Connection;

/**
 *
 * @author joanl
 */
public class HibernateDaoOrderFactory implements DAOOrderFactory{

    @Override
    public OrderDAO createDAO(Connection con) {
        return new HibernateOrderDao();
    }
    
}
