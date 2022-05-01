package bog_models;


import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

//import org.junit.Test;


@Entity
@Table(name="orders")
public class Order implements Serializable{
    @Column(name ="idNumber")
    public static int orderIDnumber;
    
    @Id
    @Column(name = "orderID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    
    @OneToOne
    @PrimaryKeyJoinColumn
    private Product product;
    
    @OneToOne
    @PrimaryKeyJoinColumn
    private Customer customer;
    
    @Column(name = "quantity")
    private int productQuantity;
    
    @Column(name = "subtotal")
    private double subtotal;
    
    @Column(name = "creationDateTime")
    private LocalDateTime creationDataTime;
    
    @Column(name = "handlingTime")
    private int handlingTime;

    private boolean isSent;

    // CONSTRUCTOR
    public Order(Product product, Customer customer, int productQuantity) {
        this.orderID = orderIdGenerator();
        this.product = product;
        this.customer = customer;
        this.productQuantity = productQuantity;
        this.subtotal = this.product.getPrice() * productQuantity;
        this.creationDataTime = LocalDateTime.now();
        this.handlingTime = 2;

    }
    public Order(){
        
    }
        

    // Generador de Order ID
    public static int orderIdGenerator(){
        orderIDnumber++;
        return orderIDnumber;
    }
    
    // CHECKERS - Comprueban las políticas de negocio y calculan resultados
    public boolean isCancellable() {

        return !isSent; // Si esta enviado, no se puede cancelar. Así de simple
    }

    public boolean clientExists() {

        return false;
    }

    public double calculateShipping() {
        final double customerDiscount = this.customer.getCustomerDiscount() / 100;
        final double discount = customerDiscount > 0 ? product.getShippingFee() * customerDiscount : product.getShippingFee();
        final double shippingWithDiscount = product.getShippingFee() - discount;
        return shippingWithDiscount;
    }

   /* @Test
    public void testCalculateShipping(){ //Para que esto funcione debemos pasar el producto 002A y un costumer.Premium.
        assertEquals(1.6d, calculateShipping(), 0.01); //El 0.001 es el margen de error que tienen los float
    }
*/
    public double calculateOrderTotal() {
        final double productPrice = this.productQuantity * this.product.getPrice();
        final double shipping = calculateShipping();
        final double orderTotal = productPrice + shipping;
        return orderTotal;
    }

   /* @Test
    public void testCalculateOrderTotal(){ // Este seria el segundo order de la lista de Data. Producto 002A y cliente Premium
        assertEquals( 41.6, calculateOrderTotal(), 0.01);
    }
*/
    public boolean orderSent() {
        // Creamos la fecha en la que el pedido estará lista
        LocalDateTime handlingResult = creationDataTime.plusDays(handlingTime);

        // Pasada esa fecha el articulo se habrá enviado
        if (LocalDateTime.now().compareTo(handlingResult) > 0) {
            isSent = true;
        } else {
            isSent = false;
        }

        return isSent;
    }

   /* @Test
    public void testOrderSent(){
        assertTrue(orderSent()); //Esto deberia ser siempre true en el momento que lo probamos
    }
    */
    // GETTERS & SETTERS

    public int getorderID() {
        return orderID;
    }

    public void setorderID(int orderID) {
        this.orderID = orderID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getproductQuantity() {
        return productQuantity;
    }

    public void setproductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDateTime getcreationDataTime() {
        return creationDataTime;
    }

    public void setcreationDataTime(LocalDateTime creationDataTime) {
        this.creationDataTime = creationDataTime;
    }

    public int gethandlingTime() {
        return handlingTime;
    }

    public void sethandlingTime(int handlingTime) {
        this.handlingTime = handlingTime;
    }

    @Override
    public String toString() {
        return "Order [creationDataTime=" + creationDataTime + ", customer=" + customer + ", handlingTime="
                + handlingTime + ", product="
                + product + ", productQuantity=" + productQuantity + ", orderID="
                + orderID
                + ", subtotal=" + subtotal + "]";
    }

}
