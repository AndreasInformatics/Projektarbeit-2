package backend;


import javax.persistence.*;

@Entity
@Table(name = "Customer")
public class CustomerAccount {
    @Column(nullable = false)
    private String prename;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String housenumber;
    @Column(nullable = false)
    private int postcode;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int customernumber;

    public CustomerAccount(String prename, String surname, String email, String location, int postcode, String street, String housenumber, String password){
        this.prename = prename;
        this.surname = surname;
        this.email = email;
        this.location = location;
        this.postcode = postcode;
        this.street = street;
        this.housenumber = housenumber;
        this.password = password;
    }

    public CustomerAccount(){
    }

    public String getPrename() {
        return prename;
    }
    public String getSurname(){
        return surname;
    }
    public String getEmail(){ return email; }
    public String getStreet() {
        return street;
    }
    public String getLocation() {
        return location;
    }
    public String getPassword(){ return password;}
    public String getHousenumber() {
        return housenumber;
    }
    public long getCustomernumber(){
        return customernumber;
    }
    public int getPostcode() {
        return postcode;
    }

    public void setPrename(String prename) {
       this.prename = prename;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setLocation(String location) { this.location = location; }
    public void setPassword(String password) { this.password = password; }
    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }
    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
    public void setCustomernumber(int customernumber) {
        this.customernumber = customernumber;
    }
}