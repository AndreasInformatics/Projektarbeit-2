import backend.*;
import backend.financialobjects.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    List<CustomerAccount> testcustomerlist = new ArrayList<>();
    Market market = new Market(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @BeforeEach
    public void setUp() {
        testcustomerlist.add(new CustomerAccount("Julian", "Müller", "julianmueller@gmail.com", "Berlin", 10583, "Bundesplatz", "3", "10Bundesplatz583"));
        testcustomerlist.add(new CustomerAccount("Sophie", "Ernst", "sophie23unicorn@gmail.com", "Hannover", 30167, "Hauptsraße", "56", "rainbow#unicorn63"));
        testcustomerlist.add(new CustomerAccount("Stefan", "Gehring", "gehring.business@firma.com", "München", 80331, "Wiesenstraße", "17", "ImogdiWiesn80331"));
        testcustomerlist.add(new CustomerAccount("Elisabeth", "Schlauberger", "itconsulting@karlsruhe.de", "Karlsruhe", 76133, "Durlacher Allee", "24", "HansestadtLove434"));

        market.getStocklist().add(new Stock("Adidas", "Germany", "A1EWWW", "DE000A1EWWW0", 65.00));
        market.getStocklist().add(new Stock("Daimler", "Germany", "710000", "DE0007100000", 75.00));
        market.getStocklist().add(new Stock("Amazon", "USA", "906866", "US0231351067", 3000.00));
    }

    /*
    @Test
    void positive_test_WithdrawAmount() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(0).getCustomernumber(), 200.00);
        bank.withdrawAmount(testcustomerlist.get(0).getCustomernumber(), 100.00);
        double balance = bank.getBalance(testcustomerlist.get(0).getCustomernumber());
        assertEquals(100.00, balance);
    }

    @Test
    void negative_test_WithdrawAmount() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(0).getCustomernumber(), 200.00);
        boolean returnvalue = bank.withdrawAmount(testcustomerlist.get(0).getCustomernumber(), 250.00);
        double balance = bank.getBalance(testcustomerlist.get(0).getCustomernumber());
        assertFalse(returnvalue);
        assertEquals(200.00, balance);
    }

    @Test
    void positive_test_DepositeAmount() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(2).getCustomernumber(), 200.00);
        bank.depositeAmount(testcustomerlist.get(2).getCustomernumber(), 400.00);
        double balance = bank.getBalance(testcustomerlist.get(2).getCustomernumber());
        assertEquals(600.00, balance);
    }

    @Test
    void positive_test_TransactionAmount() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(1).getCustomernumber(), 200.00);
        bank.getAccountbalanceByID().put(testcustomerlist.get(2).getCustomernumber(), 200.00);
        bank.transactionAmount(testcustomerlist.get(1).getCustomernumber(), testcustomerlist.get(2).getCustomernumber(), 150.00);
        double balance_of_first = bank.getBalance(testcustomerlist.get(1).getCustomernumber());
        double balance_of_second = bank.getBalance(testcustomerlist.get(2).getCustomernumber());
        assertEquals(50.00, balance_of_first);
        assertEquals(350.00, balance_of_second);
    }

    @Test
    void negative_test_TransactionAmount() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(1).getCustomernumber(), 200.00);
        boolean returnvalue = bank.transactionAmount(testcustomerlist.get(1).getCustomernumber(), testcustomerlist.get(2).getCustomernumber(), 250.00);
        assertFalse(returnvalue);
    }

    @Test
    void test_positive_doesStockExistsOnMarket() {
        Bank bank = new Bank();
        Assertions.assertThat(market.getStocklist().get(0)).isEqualToComparingFieldByField(bank.doesFinancialObjectExist("Adidas"));
    }

    @Test
    void test_negative_doesStockExistsOnMarket() {
        Bank bank = new Bank();
        Optional<FinancialObject> returnvalue = (Optional<FinancialObject>) bank.doesFinancialObjectExist("Nike");
        Assertions.assertThat(returnvalue).isEmpty();
    }

    @Test
    void test_positive_doesStockExistsInDepot() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(2).getCustomernumber(), 1000.00);
        bank.getDepotsByID().put(testcustomerlist.get(2).getCustomernumber(), new Depot());
        bank.purchaseFinancialObject(testcustomerlist.get(2).getCustomernumber(), market.getStocklist().get(0), 5);
        Assertions.assertThat(bank.getDepotsByID().get(testcustomerlist.get(2).getCustomernumber()).getInvestmentlist().get(0)).isEqualToComparingFieldByField(bank.doesInvestmentExistInDepot("Adidas", testcustomerlist.get(2).getCustomernumber()));
    }

    @Test
    void test_negative_doesStockExistsInDepot() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(2).getCustomernumber(), 1000.00);
        bank.getDepotsByID().put(testcustomerlist.get(2).getCustomernumber(), new Depot());
        bank.purchaseFinancialObject(testcustomerlist.get(2).getCustomernumber(), market.getStocklist().get(0), 5);
        Optional<Investment> returnvalue = bank.doesInvestmentExistInDepot("Nike", testcustomerlist.get(2).getCustomernumber());
        Assertions.assertThat(returnvalue).isEmpty();
    }

    @Test
    void test_PurchaseStock() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(3).getCustomernumber(), 1000.00);
        bank.getDepotsByID().put(testcustomerlist.get(3).getCustomernumber(), new Depot());
        bank.purchaseFinancialObject(testcustomerlist.get(3).getCustomernumber(), market.getStocklist().get(0), 5);
        double balance = bank.getBalance(testcustomerlist.get(3).getCustomernumber());
        double issupricemultiplied = (1000.00 - market.getStocklist().get(0).getIssueprice() * 5);
        assertEquals(balance, issupricemultiplied);
    }


    @Test
    void test_SellWholeStock() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(3).getCustomernumber(), 1000.00);
        bank.getDepotsByID().put(testcustomerlist.get(3).getCustomernumber(), new Depot());
        bank.purchaseFinancialObject(testcustomerlist.get(3).getCustomernumber(), market.getStocklist().get(0), 10);
        bank.sellWholeAmountOfInvestment(testcustomerlist.get(3).getCustomernumber(), bank.getDepotsByID().get(testcustomerlist.get(3).getCustomernumber()).getInvestmentlist().get(0), 10);
        double balance = bank.getBalance(testcustomerlist.get(3).getCustomernumber());
        assertEquals(1000.00, balance);
    }

    @Test
    void test_SellCustomAmountStock() {
        Bank bank = new Bank();
        bank.getAccountbalanceByID().put(testcustomerlist.get(3).getCustomernumber(), 1000.00);
        bank.getDepotsByID().put(testcustomerlist.get(3).getCustomernumber(), new Depot());
        bank.purchaseFinancialObject(testcustomerlist.get(3).getCustomernumber(), market.getStocklist().get(0), 10);
        bank.sellCustomAmountOfInvestment(testcustomerlist.get(3).getCustomernumber(), bank.getDepotsByID().get(testcustomerlist.get(3).getCustomernumber()).getInvestmentlist().get(0), 5);
        double balance = bank.getBalance(testcustomerlist.get(3).getCustomernumber());
        assertEquals(675.00, balance);
    }*/
}