package bog_dao.MySqlDao;

import bog_dao.DAOOrderFactory;
import bog_dao.OrderDAO;
import java.sql.Connection;

public class MySQLDaoOrderFactory implements DAOOrderFactory{

    @Override
    public OrderDAO createDAO(Connection con) {
        
        return new MySQLOrderDAO(con);
    }
    
}
