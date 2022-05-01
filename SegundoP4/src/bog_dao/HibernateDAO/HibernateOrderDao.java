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
    
    private SessionFactory sessionFactory = null;

    @Override
    public void create(Order insertado) throws DAOException {
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
    public Order readOne(String id) throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        Order o = (Order)session.get(Order.class, id);
        return o;
    }

    @Override
    public ArrayList<Order> readAll() throws DAOException {
        Session session = null;
        session = sessionFactory.getCurrentSession();
        String hib = "FROM Customers";
        Query query = session.createQuery(hib);
        ArrayList<Order> orders = (ArrayList<Order>)query.list();
        return  orders;
    }

    @Override
    public void update(Order modificado) throws DAOException {
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
    public void delete(Order eliminado) throws DAOException {
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
