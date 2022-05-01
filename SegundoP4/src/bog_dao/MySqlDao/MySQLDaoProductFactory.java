package bog_dao.MySqlDao;

import bog_dao.DAOProductFactory;
import bog_dao.ProductDAO;
import java.sql.Connection;


public class MySQLDaoProductFactory implements DAOProductFactory {

    @Override
    public ProductDAO createDAO(Connection con) {

        return new MySQLProductDAO(con);
    }
}
