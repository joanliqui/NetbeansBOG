
package bog_dao;

import java.sql.Connection;

public interface IDAOFactory<T> {
    
    public abstract T createDAO(Connection con);
}
