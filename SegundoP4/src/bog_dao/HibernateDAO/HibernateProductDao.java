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

     private SessionFactory sessionFactory = null;
    @Override
    public void create(Product insertado) throws DAOException {
        Session session = null;
        try {
            //create SessionFactory
             sessionFactory = HibernateUtil.getSessionFactory();
             session = sessionFactory.getCurrentSession();
             //Inicialize Transaccion
             session.beginTransaction();
             //Save Object
             session.save(insertado);
             //Commit the transaction to database
             session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("SessionFactory Failed." + e.getMessage());
        }
        finally{
            //Cerramos la session
            if(session != null)
                session.close();
        }
    }

    @Override
    public Product readOne(String id) throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        Product p = (Product) session.get(Product.class, id);
        return  p;
    }

    @Override
    public ArrayList<Product> readAll() throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        String hib = "FROM Customers";
        Query query = session.createQuery(hib);
        ArrayList<Product> products = (ArrayList<Product>)query.list();
        return  products;
    }

    @Override
    public void update(Product modificado) throws DAOException {
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(modificado);
            session.getTransaction().commit();
        }catch(Exception e){
            System.err.println("SessionFactory Failed." + e.getMessage());
        }
        finally{
            if(session != null)
                session.close();
        }
    }

    @Override
    public void delete(Product eliminado) throws DAOException {
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
            session.delete(eliminado);
            session.getTransaction().commit();
        }catch(Exception e){
            System.err.println("SessionFactory Failed." + e.getMessage());
        }
        finally{
            if(session != null)
                session.close();
        }
    }
    
}
