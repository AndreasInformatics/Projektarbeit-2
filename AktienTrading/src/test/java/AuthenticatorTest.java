import backend.Authenticator;
import backend.CustomerAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticatorTest {
    List<CustomerAccount> testcustomerlist = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        //testcustomerlist.add(new CustomerAccount("Julian", "Müller", "julianmueller@gmail.com", "Berlin", 10583, "Bundesplatz", "3", "10Bundesplatz583"));
        //estcustomerlist.add(new CustomerAccount("Sophie", "Ernst", "sophie23unicorn@gmail.com", "Hannover", 30167, "Hauptsraße", "56", "rainbow#unicorn63"));
        //testcustomerlist.add(new CustomerAccount("Stefan", "Gehring", "gehring.business@firma.com", "München", 80331, "Wiesenstraße", "17", "ImogdiWiesn80331"));
        //testcustomerlist.add(new CustomerAccount("Elisabeth", "Schlauberger", "itconsulting@karlsruhe.de", "Karlsruhe", 76133, "Durlacher Allee", "24", "HansestadtLove434"));
    }

    @Test
    void positive_testAuthenticatorDoesCustomerExists_first(){
        Authenticator authenticator = new Authenticator();
        assertTrue(authenticator.doesCustomerExists(testcustomerlist, "julianmueller@gmail.com", "10Bundesplatz583"));
    }

    @Test
    void negative_testAuthenticatorDoesCustomerExists_first(){
        Authenticator authenticator = new Authenticator();
        assertFalse(authenticator.doesCustomerExists(testcustomerlist, "julianmueller@gmail.com", "10Bundesplatz"));
    }

    @Test
    void positive_testAuthenticatorDoesCustomerExists_second(){
        Authenticator authenticator = new Authenticator();
        assertTrue(authenticator.doesCustomerExists(testcustomerlist,"Stefan", "Gehring", "gehring.business@firma.com"));
    }

    @Test()
    void negative_testAuthenticatorDoesCustomerExists_second(){
        Authenticator authenticator = new Authenticator();
        assertFalse(authenticator.doesCustomerExists(testcustomerlist,"Stefano", "Gehring", "gehring.business@firma.com"));
    }

    @Test
    public void positive_test_AuthenticatorGetAccount_first(){
        Authenticator authenticator = new Authenticator();
        assertEquals(testcustomerlist.get(1).getCustomernumber(), authenticator.getAccountCustomerNumber(testcustomerlist, "sophie23unicorn@gmail.com", "rainbow#unicorn63"));
    }

    @Test
    public void negative_test_AuthenticatorGetAccount_first(){
        Authenticator authenticator = new Authenticator();
        Exception exception = assertThrows(NoSuchElementException.class, ()-> authenticator.getAccountCustomerNumber(testcustomerlist, "sophie23unicorn@gmail.com", "rainbow#unicorn"));
        String expectedMessage = "Der Kunde existieret nicht im System, erstellen sie zuerst ein neues Konto";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void positive_testAuthenticatorGetAccount_second(){
        Authenticator authenticator = new Authenticator();
        assertEquals(testcustomerlist.get(3).getCustomernumber(), authenticator.getAccountCustomerNumber(testcustomerlist,"Elisabeth", "Schlauberger", "itconsulting@karlsruhe.de"));
    }

    @Test
    public void negative_testAuthenticatorGetAccount_second(){
        Authenticator authenticator = new Authenticator();
        Exception exception = assertThrows(NoSuchElementException.class, ()-> authenticator.getAccountCustomerNumber(testcustomerlist, "Elisabeth", "Schlauberg", "itconsulting@karlsruhe.de"));
        String expectedMessage = "Der Kunde existiert nicht im System, wählen sie einen anderen Adressaten";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }
}