package bog_models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "products")
public class Product implements Serializable{
    @Id
    @Column(name = "productID")
    private String productID;
    @Column(name = "productName")
    private String productName;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "shippingFee")
    private double shippingFee;
    @Column(name = "handlingTime")
    private int handlingTime;

    // CONSTRUCTOR
    public Product(String productID, String productName, String description2, double price, double shippingFee, int handlingTime) {
        this.productID = productID;
        this.productName = productName;
        this.description = description2;
        this.price = price;
        this.shippingFee = shippingFee;
        this.handlingTime = handlingTime;
    }
    
    public Product(){
        
    }
    // GETTERS & SETTERS
    public String getproductID() {
        return productID;
    }

    public void setproductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public int gethandlingTime() {
        return handlingTime;
    }

    public void sethandlingTime(int handlingTime) {
        this.handlingTime = handlingTime;
    }



}
