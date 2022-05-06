/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.DAOException;
import bog_dao.ProductDAO;
import bog_models.Product;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author joanl
 */
public class HibernateProductDao implements ProductDAO{

    @Override
    public void create(Product insertado) throws DAOException {
        try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
             //Save Object
             session.save(insertado);
             //Commit the transaction to database
             session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error from HibernateProductDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            
            HibernateUtil.closeSessionFactory();
        }
    }

    @Override
    public Product readOne(String id) throws DAOException {
         Product c = null;
        try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            c = (Product)session.get(Product.class, id);
            
        } catch (Exception e) {
            System.err.println("Error from HibernateProductDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
        return c;
    }

    @Override
    public ArrayList<Product> readAll() throws DAOException {
               ArrayList<Product> products = null;
        try{
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            String hib = "FROM Product";
            Query query = session.createQuery(hib);
            products = (ArrayList<Product>)query.list();
           // session.getTransaction().commit();
           // session.getTransaction();
            
        }catch(Exception e){
            System.err.println("Error from HibernateProductDAO.SessionFactory Failed." + e);
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
       
        return  products;
    }

    @Override
    public void update(Product modificado) throws DAOException {
        try{
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            session.update(modificado);
            session.getTransaction().commit();
        }catch(Exception e){
            System.err.println("SessionFactory Failed." + e.getMessage());
        }
        finally{
           HibernateUtil.closeSessionFactory();
        }
    }

    @Override
    public void delete(Product eliminado) throws DAOException {
        try{
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.delete(eliminado);
            session.getTransaction().commit();
        }catch(Exception e){
            System.err.println("SessionFactory Failed." + e.getMessage());
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
    }
    
}
