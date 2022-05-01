package bog_dao.MySqlDao;

import bog_dao.CustomerDAO;
import bog_dao.DAOCustomerFactory;
import java.sql.Connection;

public class MySQLDaoCustomerFactory implements DAOCustomerFactory {

    @Override
    public CustomerDAO createDAO(Connection con) {
        
        return new MySQLCustomerDAO(con);
    }
    
}
