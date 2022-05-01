/* orders
+------------------+-------------+------+-----+---------+----------------+
| Field            | Type        | Null | Key | Default | Extra          |
+------------------+-------------+------+-----+---------+----------------+
| orderID          | int(11)     | NO   | PRI | NULL    | auto_increment |
| productID        | varchar(5)  | YES  | MUL | NULL    |                |
| customerEmail    | varchar(50) | YES  | MUL | NULL    |                |
| productQuantity  | int(11)     | YES  |     | NULL    |                |
| subtotal         | double      | YES  |     | NULL    |                |
| creationDateTIme | datetime    | YES  |     | NULL    |                |
| hadnlingTime     | int(11)     | YES  |     | NULL    |                |
| isSent           | tinyint(4)  | YES  |     | 0       |                |
+------------------+-------------+------+-----+---------+----------------+
*/ 


package bog_dao.MySqlDAO;

import bog_models.Customer;
import java.util.ArrayList;

import bog_controllers.CustomersController;
import bog_controllers.ProductsController;
import bog_dao.DAOException;
import bog_dao.OrderDAO;
import bog_models.Order;
import bog_models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDateTime;


public class MySQLOrderDAO implements OrderDAO {
    
    final String INSERT = "INSERT INTO orders (orderID, productID, customerEmail, productQuantity, subtotal, creationDateTime, handlingTime, isSent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE orders SET orderID = ?, productID = ?, customerEmail = ?, productQuantity = ?, subtotal = ?, creationDateTime = ?, handlingTime = ?, isSent = ?";
    final String DELETE = "DELETE FROM orders WHERE orderID = ?";
    final String GETALL = "SELECT orderID, productID, customerEmail, productQuantity, subtotal, creationDateTime, handlingTime, isSent FROM orders";
    final String GETONE = "SELECT orderID, productID, customerEmail, productQuantity, subtotal, creationDateTime, handlingTime, isSent FROM orders WHERE orderID = ?";


    private final Connection conn;
    
    public MySQLOrderDAO(Connection conn){
        this.conn=conn;}

    
    @Override
    public void create(Order insertado) throws DAOException {
    PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setInt(1, insertado.getorderID());
            stat.setString(2, insertado.getProduct().toString());
            stat.setString(3, insertado.getCustomer().toString());
            stat.setInt(4, insertado.getproductQuantity());
            stat.setDouble(5, insertado.getSubtotal());
            stat.setString(6, insertado.getcreationDataTime().toString());
            stat.setInt(7, insertado.gethandlingTime());
            stat.setBoolean(8,insertado.isCancellable());
            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que el pedido no se haya guardado");
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
    
        private Order convertir(ResultSet rs) throws SQLException{
        // Product product = Product.valueOf(rs.getString("productID"));
        ProductsController pc = null;  // ESTO NO SIRVE
        String productID = rs.getString("productID");
        // Customer customer = Customer.getCustomer(rs.getString("customerEmail"));
        CustomersController cc = null;
        String customerEmail = rs.getString("customerEmail");
        int productQuantity = rs.getInt("productQuantity");
        Double subtotal = rs.getDouble("subtotal");
        LocalDateTime creationDataTime =  rs.getTimestamp(6).toLocalDateTime();
        int handlingTime = rs.getInt("handlingTime");
        Boolean isSent=rs.getBoolean("isSent");
        Order order = new Order (pc.returnProduct(productID), cc.returnCustomer(customerEmail), productQuantity);
        return order;  
        }

    @Override
    public Order readOne(String id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Order order = null;
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1, id);
            rs = stat.executeQuery();
            if (rs.next()){
                order = convertir(rs);
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
    return order;
    }

    @Override
    public ArrayList<Order> readAll() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        ArrayList<Order> orders = new ArrayList<>();
        try {
            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()){
                orders.add(convertir(rs));
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
        return orders;
    }

    @Override
    public void update(Order modificado) throws DAOException {
        PreparedStatement stat=null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setInt(1, modificado.getorderID());
            stat.setString(2, modificado.getProduct().getproductID());
            stat.setString(3, modificado.getCustomer().getEmail());
            stat.setInt(4, modificado.getproductQuantity());
            stat.setDouble(5, modificado.getSubtotal());
            stat.setString(6, modificado.getcreationDataTime().toString());
            stat.setInt(7,modificado.gethandlingTime());
            stat.setBoolean(8,modificado.orderSent());

            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que el pedido no se haya guardado");
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
        }        
    }

    @Override
    public void delete(Order eliminado) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, eliminado.getorderID());
            if (stat.executeUpdate() ==0) {
                throw new DAOException ("Puede que el pedido no se haya eliminado");
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
