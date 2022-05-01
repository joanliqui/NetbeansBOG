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


/**
 *
 * @author joanl
 */
public class HibernateCustomerDao implements  CustomerDAO{

    private SessionFactory sessionFactory = null;

    public HibernateCustomerDao() {
    }
    
    @Override
    public void create(Customer insertado) throws DAOException {
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
    public Customer readOne(String id) throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        Customer c = (Customer)session.get(Customer.class, id);
        return c;
    }

    @Override
    public ArrayList<Customer> readAll() throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        String hib = "FROM Customers";
        Query query = session.createQuery(hib);
        ArrayList<Customer> customers = (ArrayList<Customer>)query.list();
        return  customers;
    }

    @Override
    public void update(Customer modificado) throws DAOException {
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
    public void delete(Customer eliminado) throws DAOException {
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
