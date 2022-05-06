/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bog_dao.HibernateDAO;

import bog_models.Customer;
import bog_models.Order;
import bog_models.Product;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.MetadataSource;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author joanl
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;
    
    public static void buildSessionFactory(){
        
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Order.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.err.println("Imposible crear la sessionFactory" + e);
        }
    }
    
    private static void openSession(){
        
        session = sessionFactory.openSession();
    }
    
    public static  Session getCurrentSession(){
        if((session == null) || !session.isOpen()){
            openSession();
        }
        return  session;
    }
    public static  void closeSessionFactory(){
        if(session != null)
            session.close();
        
        if(sessionFactory != null)
            sessionFactory.close();
    }
    
    /*
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
        {    
            try{
                Map<String, String> settings = new HashMap<>();
                settings.put("connection.driver_class", "com.mysql.jdbc.Driver");
                settings.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/P5_Prod3_BOG?zeroDateTimeBehavior=convertToNull");
                settings.put("hibernate.connection.username", "bog");
                settings.put("hibernate.connection.password", "bog");
                settings.put("hibernate.current_session_context_class", "thread");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.format_sql", "true");

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(Customer.class);
                metadataSources.addAnnotatedClass(Product.class);
                metadataSources.addAnnotatedClass(Order.class);

                Metadata metadata = metadataSources.buildMetadata();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }
            catch(Throwable cause){
                System.err.println("Error from HibernateUtil. Building Session Factory:" + cause);
            }
          
        }
        
        return sessionFactory;
    }
    */
}
