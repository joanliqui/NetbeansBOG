/* products
+--------------+--------------+------+-----+---------+-------+
| Field        | Type         | Null | Key | Default | Extra |
+--------------+--------------+------+-----+---------+-------+
| productID    | varchar(5)   | NO   | PRI | NULL    |       |
| productName  | varchar(45)  | YES  |     | NULL    |       |
| description  | varchar(500) | YES  |     | NULL    |       |
| price        | double       | YES  |     | NULL    |       |
| shippingFee  | double       | YES  |     | NULL    |       |
| handlingTime | int(11)      | YES  |     | NULL    |       |
+--------------+--------------+------+-----+---------+-------+
 */
package bog_dao.MySqlDao;
import bog_dao.DAOException;
import bog_dao.ProductDAO;
import bog_models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLProductDAO implements ProductDAO{
    //Los ? son para luego el PreparedStatement
    final String INSERT = "INSERT INTO products VALUES( ?, ?, ?, ?, ?, ?);";
    final String UPDATE = "UPDATE products SET productID = ?, productName = ?, description = ?, price = ?, shippingFee = ?, handlingTime = ?, WHERE productID = ?";
    final String DELETE = "DELETE FROM products WHERE productID = ?";
    final String GETALL = "SELECT * FROM products";
    final String GETONE = "SELECT productID, productName, description, price, shippingFee, handlingTime WHERE productID = ?";
    
    private final Connection conn;
    
    public MySQLProductDAO(Connection conn) {
        this.conn=conn; 
    }
    
    @Override
    public void create(Product insertado) throws DAOException{
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, insertado.getproductID());
            stat.setString(2, insertado.getProductName());
            stat.setString(3, insertado.getDescription());
            stat.setDouble(4, insertado.getPrice());
            stat.setDouble(5, insertado.getShippingFee());
            stat.setInt(6, insertado.gethandlingTime());
            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que el producto no se haya guardado");
        }
        } catch (SQLException ex) {
            throw new DAOException("Error en SQL", ex);
        } finally {
            if (stat != null){
                try {
                    stat.close();
                }catch (SQLException ex){
                    throw new DAOException ("Error en SQL", ex);
                }
            }
        }   
    }
    
    private Product convertir(ResultSet rs) throws SQLException{
        String productID = rs.getString(1);
        String productName = rs.getString(2);
        String description = rs.getString(3);
        Double price = rs.getDouble(4);
        Double shippingFee = rs.getDouble(5);
        int handlingTime = rs.getInt(6);
        Product product = new Product (productID, productName, description, price, shippingFee, handlingTime);
        return product;  
        }
    
  @Override
    public Product readOne(String id) throws DAOException {
 PreparedStatement stat = null;
        ResultSet rs = null;
        Product producto = null;
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
            rs = stat.executeQuery();
            if (rs.next()){
                producto = convertir(rs);
            } else{
                throw new DAOException("No se ha encontrado este pedido");
        }
    }catch (SQLException ex){
        throw new DAOException ("Error en SQL", ex);
    } finally {
            if(rs != null){
                try{
                    rs.close();
                } catch(SQLException ex){
                    new DAOException ("Error en SQL", ex);
            }
        }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            }
    }
    return producto;    
    }

    @Override
    public ArrayList<Product> readAll() throws DAOException {
       PreparedStatement stat = null;
        ResultSet rs = null;
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()){
                products.add(convertir(rs));
            }
        } catch (SQLException ex) {
            throw new DAOException("Error en SQL", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            } 
            if (stat != null) {
                try{
                    stat.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return products;    }
    
    @Override
    public void update(Product modificado) throws DAOException{
  PreparedStatement stat=null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, modificado.getproductID());
            stat.setString(2, modificado.getProductName());
            stat.setString(3, modificado.getDescription());
            stat.setDouble(5, modificado.getPrice());
            stat.setDouble(6, modificado.getShippingFee());
            stat.setInt(7,modificado.gethandlingTime());

            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que el producto no se haya guardado");
        }
        } catch (SQLException ex){
            throw new DAOException ("Error de SQL", ex);
        } finally {
           if (stat !=null){
           try{
           stat.close();
           }catch (SQLException ex) {
               throw new DAOException ("Error de SQL", ex);
                }    
           }
        }     }

    @Override
    public void delete(Product eliminado) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, eliminado.getproductID());
            if (stat.executeUpdate() ==0) {
                throw new DAOException ("Puede que el producto no se haya eliminado");
            }
            } catch (SQLException ex){
                throw new DAOException ("Error de SQL", ex);
            } finally {
                if (stat != null){
                    try{
                        stat.close();
                    }catch (SQLException ex){
                        throw new DAOException("Error de SQL", ex);
                    }
                }
            }
    
            
    }    

    
    
}
