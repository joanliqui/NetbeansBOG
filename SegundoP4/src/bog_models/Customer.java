package bog_models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="customers")
public class Customer implements  Serializable{
    @Column(name = "firstname")
    protected String firstname;
    
    @Column(name = "lastname")
    protected String lastname;
    
    @Id
    @Column(name = "email")
    protected String email;
    
    @Column(name = "adress")
    protected String address;
    
    @Column(name = "idCardNumber")
    protected String idCardNumber;
    
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    protected CustomerType type;
    
    @Column(name = "customerFee")
    protected double customerFee;
    
    @Column(name = "customerDiscount")
    protected int customerDiscount;

    public Customer() {
    }

    // CONSTRUCTOR
    public Customer(String firstname, String lastname, String email, String address, String idCardNumber,
            CustomerType type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.idCardNumber = idCardNumber;
        this.customerDiscount = calculateDiscount();
        this.type = type;
        this.customerFee = calculateSippingFee();
    }

    private int calculateDiscount() {
        return this.type == CustomerType.PREMIUM ? 20 : 0;
    }

    private double calculateSippingFee(){
        return this.type == CustomerType.PREMIUM ? 30 : 0;
    }

    // SETTERS & GETTERS
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNumber() {
        return this.idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public CustomerType getType() {
        return this.type;
    }

    public void setType(CustomerType type) {
       this.type = type;
    }

    public double getCustomerFee() {
        return this.customerFee;
    }

    public void setCustomerFee(double customerFee) {
        this.customerFee = customerFee;
    }

    public int getCustomerDiscount() {
        return this.customerDiscount;
    }

    public void setCustomerDiscount(int customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

   
}
