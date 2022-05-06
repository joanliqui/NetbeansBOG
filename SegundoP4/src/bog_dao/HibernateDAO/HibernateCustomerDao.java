/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.CustomerDAO;
import bog_dao.DAOException;
import bog_models.Customer;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateCustomerDao implements  CustomerDAO{
    
    @Override
    public void create(Customer insertado) throws DAOException {
        try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
             //Save Object
             session.save(insertado);
             //Commit the transaction to database
             session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error from HibernateCustomerDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            
            HibernateUtil.closeSessionFactory();
        }
    }

    @Override
    public Customer readOne(String id) throws DAOException {
        Customer c = null;
        try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            c = (Customer)session.get(Customer.class, id);
            
        } catch (Exception e) {
            System.err.println("Error from HibernateCustomerDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
        return c;
    }

    @Override
    public ArrayList<Customer> readAll() throws DAOException {
        ArrayList<Customer> customers = null;
        try{
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            String hib = "FROM Customer";
            Query query = session.createQuery(hib);
            customers = (ArrayList<Customer>)query.list();
           // session.getTransaction().commit();
           // session.getTransaction();
            
        }catch(Exception e){
            System.err.println("Error from HibernateCustomerDAO.SessionFactory Failed." + e);
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
       
        return  customers;
    }

    @Override
    public void update(Customer modificado) throws DAOException {
        
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
    public void delete(Customer eliminado) throws DAOException {
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
