package backend;

import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("java:S106")
public class Authenticator {
    public boolean doesCustomerExists(List<CustomerAccount> customerlist, String email, String password){
        if (customerlist.stream().anyMatch(customer -> email.equals(customer.getEmail()) && password.equals(customer.getPassword()))) {
            return true;
        } else {
            return false;
        }
    }
    public boolean doesCustomerExists(List<CustomerAccount> customerlist, String prename, String surname, String email){
        if (customerlist.stream().anyMatch(customer -> prename.equals(customer.getPrename()) && surname.equals(customer.getSurname()) && email.equals(customer.getEmail()))) {
            return true;
        } else {
            return false;
        }
    }

    public long getAccountCustomerNumber(List<CustomerAccount> customerlist, String email, String password){
        CustomerAccount newcustomer = customerlist.stream().filter(customer -> email.equals(customer.getEmail()) && password.equals(customer.getPassword())).findAny().orElse(null);
        if (newcustomer != null) {
            return newcustomer.getCustomernumber();
        } else {
            throw new NoSuchElementException("Der Kunde existieret nicht im System, erstellen sie zuerst ein neues Konto");
        }
    }
    public long getAccountCustomerNumber(List<CustomerAccount> customerlist, String prename, String surname, String email){
         CustomerAccount newcustomer = customerlist.stream().filter(customer -> prename.equals(customer.getPrename()) && surname.equals(customer.getSurname()) && email.equals(customer.getEmail())).findAny().orElse(null);
        if (newcustomer != null) {
            return newcustomer.getCustomernumber();
        } else {
            throw new NoSuchElementException("Der Kunde existiert nicht im System, wählen sie einen anderen Adressaten");
        }
    }
}
