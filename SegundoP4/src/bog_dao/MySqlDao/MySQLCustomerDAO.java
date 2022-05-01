/* customers
+------------------+---------------------------+------+-----+---------+-------+
| Field            | Type                      | Null | Key | Default | Extra |
+------------------+---------------------------+------+-----+---------+-------+
| email            | varchar(50)               | NO   | PRI | NULL    |       |
| firstname        | varchar(45)               | YES  |     | NULL    |       |
| lastname         | varchar(45)               | YES  |     | NULL    |       |
| address          | varchar(200)              | YES  |     | NULL    |       |
| idCardNumber     | varchar(20)               | YES  |     | NULL    |       |
| customerFee      | double                    | YES  |     | NULL    |       |
| customerDiscount | int(11)                   | YES  |     | NULL    |       |
| type             | enum('PREMIUM','REGULAR') | YES  |     | NULL    |       |
+------------------+---------------------------+------+-----+---------+-------+
*/


package bog_dao.MySqlDao;

import bog_dao.CustomerDAO;
import bog_dao.DAOException;
import java.sql.Connection;
import java.util.ArrayList;
import bog_models.Customer;
import bog_models.CustomerType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;


public class MySQLCustomerDAO implements CustomerDAO{
    
    final String INSERT = "INSERT INTO customers(email, firstname, lastname, address, idCardNumber, customerFee, customerDiscount, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE customers SET email = ?, firstname = ?, address = ?, idCardNumber = ?, customerFee = ?, customerDiscount = ?, type = ?";
    final String DELETE = "DELETE FROM customers WHERE email = ?";
    final String GETALL = "SELECT email, firstname, lastname, address, idCardNumber, customerFee, customerDiscount, type FROM customers";
    final String GETONE = "SELECT email, firstname, lastname, address, idCardNumber, customerFee, customerDiscount, type FROM customers WHERE email = ?";
    
    private Connection conn;
    
    public MySQLCustomerDAO(Connection conn){
        this.conn=conn;}


    @Override
    public void create(Customer insertado) throws DAOException{
    PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, insertado.getEmail());
            stat.setString(2, insertado.getFirstname());
            stat.setString(3, insertado.getLastname());
            stat.setString(4, insertado.getAddress());
            stat.setString(5, insertado.getIdCardNumber());
            stat.setDouble(6, insertado.getCustomerFee());
            stat.setInt(7, insertado.getCustomerDiscount());
            stat.setString(8,insertado.getType().toString());
            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que no se haya guardado");
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
    
    private Customer convertir(ResultSet rs) throws SQLException{
        String email = rs.getString("email");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String address = rs.getString("address");
        String idCardNumber = rs.getString("idCardNumber");
        CustomerType type =  CustomerType.valueOf(rs.getString("type"));
        Customer customer = new Customer (email, firstname, lastname, address, idCardNumber, type);
        return customer;


    }
    
    @Override
    public Customer readOne(String email) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Customer customer = null;
        try{
            stat = conn.prepareStatement(GETONE);
            stat.setString(1, email);
            rs = stat.executeQuery();
            if (rs.next()){
                customer = convertir(rs);
            } else{
                throw new DAOException("No se ha encontrado ese registro");
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
    return customer;
    }


    @Override
    public ArrayList<Customer> readAll() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()){
                customers.add(convertir(rs));
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
        return customers;
    
    }
    
    @Override
    public void update(Customer modificado) throws DAOException{
        PreparedStatement stat=null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, modificado.getFirstname());
            stat.setString(2, modificado.getFirstname());
            stat.setString(3, modificado.getLastname());
            stat.setString(4, modificado.getAddress());
            stat.setString(5, modificado.getIdCardNumber());
            stat.setDouble(6, modificado.getCustomerFee());
            stat.setInt(7, modificado.getCustomerDiscount());
            stat.setString(8,modificado.getType().toString());
            if (stat.executeUpdate() ==0){
                throw new DAOException("Puede que no se haya guardado");
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
    public void delete(Customer eliminado) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setString(1, eliminado.getEmail());
            if (stat.executeUpdate() ==0) {
                throw new DAOException ("Puede que el cliente no se haya eliminado");
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
    
    }}

