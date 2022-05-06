/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_dao.DAOException;
import bog_dao.OrderDAO;
import bog_models.Order;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author joanl
 */
public class HibernateOrderDao implements OrderDAO{
    
    @Override
    public void create(Order insertado) throws DAOException {
         try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
             //Save Object
             session.save(insertado);
             //Commit the transaction to database
             session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error from HibernateOrderDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            
            HibernateUtil.closeSessionFactory();
        }
    }

    @Override
    public Order readOne(String id) throws DAOException {
        Order c = null;
        try {
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            c = (Order)session.get(Order.class, id);
            
        } catch (Exception e) {
            System.err.println("Error from HibernateOrderDAO.SessionFactory Failed." + e.getMessage());
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
        return c;
    }

    @Override
    public ArrayList<Order> readAll() throws DAOException {
        ArrayList<Order> orders = null;
        try{
            HibernateUtil.buildSessionFactory();
            Session session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            String hib = "FROM Order";
            Query query = session.createQuery(hib);
            orders = (ArrayList<Order>)query.list();
           // session.getTransaction().commit();
           // session.getTransaction();
            
        }catch(Exception e){
            System.err.println("Error from HibernateOrderDAO.SessionFactory Failed." + e);
        }
        finally{
            HibernateUtil.closeSessionFactory();
        }
       
        return  orders;
    }

    @Override
    public void update(Order modificado) throws DAOException {
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
    public void delete(Order eliminado) throws DAOException {
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
