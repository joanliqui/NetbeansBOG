package bog_models;

import java.sql.SQLException;
import java.util.ArrayList;

import bog_dao.DAOException;
import bog_dao.HibernateDAO.HibernateDaoManager;
import bog_dao.IDaoManager;
import bog_dao.MySqlDao.MySQLDaoManager;


public class Data {
    private HibernateDaoManager daoManager;
    private ArrayList<Customer> customers;
    private ArrayList<Product> products;
    private ArrayList<Order> orders;
    
   
    public Data() throws DAOException {
        this.daoManager = new HibernateDaoManager(); 
        this.customers = readDBCustomers();
        this.products = readDBProducts();
        this.orders = readDBOrders();
    }
   
    
    
    // Customer data functions ******************************************************************
    public ArrayList<Customer> getCustomers() {
        return this.customers;
    }

    public ArrayList<Customer> readDBCustomers() throws DAOException {
        return daoManager.getCustomerDAO().readAll();
    }

    public ArrayList<Customer> getPremiumCustomers(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (int i=0; i < this.customers.size(); i++){
            if (this.customers.get(i).getType() == CustomerType.PREMIUM){
                customers.add(this.customers.get(i));
            }
        }
        return customers;
    }
    
    public ArrayList<Customer> getRegularCustomers(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (int i=0; i < this.customers.size(); i++){
            if (this.customers.get(i).getType() == CustomerType.REGULAR){
                customers.add(this.customers.get(i));
            }
        }
        return customers;
    }

    public void addCustomer(Customer customer) throws Exception {
        try {
            this.daoManager.getCustomerDAO().create(customer); 
            this.customers = readDBCustomers();
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean existsCustomer(String email){
        boolean ret = false; 
        for (int i=0; i < this.customers.size(); i++){
            if (email.equals(this.customers.get(i).getEmail())){
                ret = true;
            }
        }
       
        return ret;
    }
    
    public int lenghtCustomer(){
        return this.customers.size(); // Devuelve int con longitiud del array Customer
    }

    public void deleteCustomer(Customer customer) throws DAOException {
        this.daoManager.getCustomerDAO().delete(customer);
        this.customers = readDBCustomers();
    }

    // Product data functions ******************************************************************
    public ArrayList<Product> getProducts() {
        return this.products;
    }
    
    public ArrayList<Product> readDBProducts() throws DAOException {
        return daoManager.getProductDAO().readAll();
    }

    public void addProduct(Product product) throws Exception {
        try {
            this.daoManager.getProductDAO().create(product);
            this.products = readDBProducts();
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteProduct(Product product) throws DAOException {
        this.daoManager.getProductDAO().delete(product);
        this.products = readDBProducts();
    }

    public int lenghtProduct (){
        return this.products.size();
    }
    // Order data functions ******************************************************************
    public ArrayList<Order> getOrders() {
        return this.orders;
    }
    public ArrayList<Order> readDBOrders() throws DAOException {
        return daoManager.getOrderDAO().readAll();
    }


    public void addOrder(Order order) throws Exception {
        try {
            this.daoManager.getOrderDAO().create(order); 
            this.orders = readDBOrders();
        } catch (Exception e) {
            throw e;
        }

    }


    public void deleteOrder(Order order) throws DAOException {
        this.daoManager.getOrderDAO().delete(order);
        this.orders = readDBOrders();
    }

    public int lenghtOrders(){
        return this.orders.size();
    }

    public ArrayList<Order> getSentOrders(){
        ArrayList<Order> ordersSent = new ArrayList<Order>();
        for (int i = 0; i < this.orders.size(); i++){
            if (this.orders.get(i).orderSent()){
                ordersSent.add(this.orders.get(i));
            }
        }
        return ordersSent; 
    }

    public ArrayList<Order> getPendingOrders(){
        ArrayList<Order> ordersPending = new ArrayList<Order>();
        for (int i = 0; i < this.orders.size(); i++){
            if (!this.orders.get(i).orderSent()){
                ordersPending.add(this.orders.get(i));

            }
        }
        return ordersPending; 
    }






    // private void cargarDatos(){
        // Customer customer = new Customer("Paco", "Saiz", "psaiz@gmail.com", "C/Toledo 178", "12345678A", CustomerType.REGULAR);
        // try {
            
        //     addCustomer(customer);
        //     customer = new Customer("Jose", "Perez", "jper3z@gmail.com", "C/desesperao 78", "19835648S", CustomerType.REGULAR);
        //     addCustomer(customer);
        //     customer = new Customer("Maria", "Hernandez", "mariahernan@gmail.com", "Av.Balcao 197", "36184656Y", CustomerType.PREMIUM);
        //     addCustomer(customer);
        //     customer = new Customer("Sara", "Cabrer", "tupacsacura@gmail.com", "C/Verano 2", "97385673U", CustomerType.PREMIUM);
        //     addCustomer(customer);
        //     customer = new Customer("Samuel", "Bolivar", "samusitoBol@gmail.com", "C/Colorado 34", "92648762F", CustomerType.REGULAR);
        //     addCustomer(customer);
        // } catch (Exception e) {
            
        // }

        // Product product = new Product("001A", "Aspirador TORIEL ", "La aspiradora capaz de limpiar la casa despues de una fiesta de fraternidad", 29, 1.4, 2);
        // try {
        //     addProduct(product);
        //     product = new Product("002A", "Aspirador TORIEL mini", "La aspiradora de mano capaz de limpiar el macdonald despues de un buffet libre", 20, 2, 2);
        //     addProduct(product);
        //     product = new Product("003A", "Cascos wireless SAMUSNG Azules", "Cascos marca SAMUSNG de color azul sin cable con cargador incluido", 46, 3.4, 1);
        //     addProduct(product);
        //     product = new Product("004A", "Reloj mecanico MANLESS", "Reloj de muñeca mecanico dorado con agujas de plata cristalizada y cristal templado resistente a todo tipo de golpe", 54.9, 5, 4);
        //     addProduct(product);
        //     product = new Product("005A", "Deportivas NIKE bump fresh 4", "Deportivas NIKE de color negras y rojas, Sirven para caminar mas rapido, creo", 60.9, 2.5, 2);
        //     addProduct(product);
        //     product = new Product("006A", "Gorra NEW ERA New York classic 1976", "Una de las gorras clasicas de la compañia NEW ERA. La primera New York que salio", 75.9, 4, 5);
        //     addProduct(product);
            
        // } catch (Exception e) {
            
        //}
        // Order order = new Order("001A", "db@gmail.com", 1,);
        // try {
        //     addOrder(order);
        //     order = new Order("002A", "jper3z@gmail.com", 2);
        //     addOrder(order);
        //     order = new Order("004A", "om@uoc.edu", 1);
        //     addOrder(order);
        //     order = new Order("A007", "samusitoBol@gmail.com", 3);
        //     addOrder(order);

        // } catch (Exception e) {}
    // }
            
    
}
   





 