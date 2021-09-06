package frontend;

import backend.*;
import backend.financialobjects.Stock;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;

import javax.persistence.*;
import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings({"java:S106", "java:S6212", "java:S1117", "java:S2140", "java:S117", "java:S2175"})
public class Controller {
    private Bank bank = new Bank();

    public void requestWork(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wie wollen sie fortfahren?");
        System.out.println("========================================");
        System.out.println("Wenn sie ein neues Kundenkonto erstellen wollen, geben sie \"Neukunde\" ein");
        System.out.println("Wenn sie sich bei ihrem Kundenkonto anmelden wollen, geben sie \"Anmeldung\" ein");
        System.out.println("========================================");
        String input = scanner.nextLine().toLowerCase(Locale.ROOT);

        if (input.equals("neukunde")) {
            createNewAccount();
            loginAccount();
        } else if (input.equals("anmeldung")) {
            loginAccount();
        } else {
            System.out.println("");
            System.out.println("!!! -- Die Eingabe war fehlerhaft. Bitte versuchen sie es erneut. -- !!!");
            System.out.println("");
            requestWork();
        }
    }

    private void createNewAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geben sie ihren Vorname an");
        String prename = scanner.nextLine();
        System.out.println("Geben sie ihren Nachnamen an");
        String surname = scanner.nextLine();
        System.out.println("Geben sie ihre Email Adresse an");
        String email = scanner.nextLine().toLowerCase(Locale.ROOT);
        System.out.println("Geben sie ihren Wohnort an");
        String location = scanner.nextLine();
        System.out.println("Geben sie ihre Postleitzahl an");
        int postcode = Integer.parseInt(scanner.nextLine());
        System.out.println("Geben sie ihren Straßenname an");
        String street = scanner.nextLine();
        System.out.println("Geben sie ihre Hausnummer an");
        String housenumber = scanner.nextLine();
        System.out.println("Geben sie ihr neues Passwort an");
        String password1 = scanner.nextLine();
        System.out.println("Wiederholen sie die Eingabe ihres Passworts");
        String password2 = scanner.nextLine();

        if(password1.equals(password2)){
            javax.persistence.EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
            EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
            EntityTransaction et = null;
            CustomerAccount customer = new CustomerAccount(prename, surname, email, location, postcode, street, housenumber, password1);
            Bank bank = new Bank(500.00);
            Depot depot = new Depot();
            try{
                et = em.getTransaction();
                et.begin();
                em.persist(customer);
                em.persist(bank);
                em.persist(depot);
                et.commit();
            } catch (Exception ex){
                if(et != null){
                    et.rollback();
                }
                ex.printStackTrace();
            }
            finally {
                em.close();
                ENTITY_MANAGER_FACTORY.close();
            }
        }
        System.out.println("========================================");
        System.out.println("!!! -- Ihr neues Kundenkonto wurde angelegt -- !!!");
        System.out.println("========================================");
    }

    private void loginAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Anmedlung: ");
        System.out.println("Geben sie ihre E-Mail Adresse an");
        String email = scanner.nextLine().toLowerCase(Locale.ROOT);
        System.out.println("Geben sie ihr Passwort an");
        String password = scanner.nextLine();

        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try{
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email=:email AND c.password=:password", CustomerAccount.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            CustomerAccount account = (CustomerAccount) query.getSingleResult();
            if(account != null){
                System.out.println("");
                System.out.println("Die Anmeldung war erfolgreich! Sie werden weitergeleitet.");
                System.out.println("");
                logedinAccount(account.getCustomernumber());
            }
        } catch (NoResultException ex){
            System.out.println("========================================");
            System.out.println("!!! --- E-Mail Adresse oder Passwort stimmen nicht überein --- !!!!!! --- Geben sie erneut E-Mail und Passwort an --- !!!");
            System.out.println("========================================");
            loginAccount();
        }
        finally {
            em.close();
        }
        ENTITY_MANAGER_FACTORY.close();
    }

    private void logedinAccount(long costumernumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wie wollen sie fortfahren?");
        System.out.println("========================================");
        System.out.println("Wenn sie Geld abheben oder einzahlen wollen, geben sie \"Abhebung\" oder \"Einzahlung\" ein");
        System.out.println("Wenn sie Geld auf ein anderes Konto überweisen wollen, geben sie \"Transaktion\" ein");
        System.out.println("Wenn sie ihren Kontostand erfahren wollen, geben sie \"Kontostand\" ein");
        System.out.println("Wenn sie mit Aktien handeln wollen, geben sie \"Aktienhandel\" ein");
        System.out.println("Wenn sie mit ETFs handeln wollen, geben sie \"ETFhandel\" ein");
        System.out.println("Wenn sie mit Cryptos handeln wollen, geben sie \"Cryptohandel\" ein");
        System.out.println("Wenn sie mit Anleihen handeln wollen, geben sie \"Anleihhandel\" ein");
        System.out.println("Wenn sie sich Abmelden wollen, geben sie \"Abmelden\" ein");
        System.out.println("Wenn sie ihr Kundenkonto schließen wollen, geben sie \"Kuendigung\" ein");
        System.out.println("========================================");
        String input = scanner.nextLine();

        switch (input.toLowerCase(Locale.ROOT)){
            case "aktienhandel":
                tradingStocks(costumernumber);
                break;
            default:
                logedinAccount(costumernumber);
                break;
        }
    }

    public double getDifferenceBetweenInputAmountAndDepotAmount(double stockamount, Investment investment){
        return (investment.getAmount() - stockamount);
    }


    private void buyfinancialObject(long customernumber) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("!!! --- Geben sie die ISIN oder den Namen des Finanz-Objektes an, dass sie kaufen möchten --- !!!");
        String investmentname = scanner.nextLine();
        System.out.println("!!! --- Geben sie an wieviele Aktien sie kaufen möchten --- !!!");
        double investmentamount = scanner.nextInt();

        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Stock c WHERE c.name = :name OR c.isin = :isin OR c.wkn = :wkn");
            query.setParameter("name", investmentname);
            query.setParameter("isin", investmentname);
            query.setParameter("wkn", investmentname);
            Stock stock = (Stock) query.getSingleResult();
            if (stock != null) {
                try {
                    Query query1 = em.createQuery("SELECT balance FROM Bankaccount WHERE customernumber = :customernumber");
                    query.setParameter("customernumber", customernumber);
                    double balance = (double) query1.getSingleResult();
                    if (balance >= (stock.getIssueprice()*investmentamount)) {
                        bank.purchaseFinancialObject(customernumber, stock, investmentamount);
                        System.out.println("!!! --- Ihr Depot enthält nun " + investmentamount + "X " + stock.getFinancialObjectname() + " || ISIN:" + stock.getIsin() + " || zu einem Betrag von " + (stock.getIssueprice() * investmentamount) + "€ --- !!!");
                    } else {
                        System.out.println("========================================");
                        System.out.println("!!! --- Zu wenig Geld auf dem Konto --- !!!");
                        System.out.println("========================================");
                    }
                } catch (NoResultException ex) {
                    System.out.println("========================================");
                    System.out.println("!!! --- Fehler bei der Datenabfrage von ihrem Depot --- !!!");
                    System.out.println("========================================");
                    loginAccount();
                }
            }
        } catch (NoResultException ex) {
            System.out.println("========================================");
            System.out.println("!!! --- Finanz-Objekt wurde nicht im System gefunden --- !!!");
            System.out.println("========================================");
            loginAccount();
        } finally {
            em.close();
        }
        ENTITY_MANAGER_FACTORY.close();
        logedinAccount(customernumber);
    }

/*
    private void sellInvestment(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("!!! --- Geben sie ISIN oder den Namen des Investment-Objektes an, dass sie verkaufen möchten --- !!!");
        String investmentname = scanner.nextLine();
        System.out.println("!!! --- Geben sie an wieviele Aktien sie von dem Unternehmen verkaufen möchten --- !!!");
        int investmentamount = scanner.nextInt();


        if(investment.isPresent()) {
            double difference = getDifferenceBetweenInputAmountAndDepotAmount(investmentamount, investment.get());
            if (difference > 0) {
                bank.sellCustomAmountOfInvestment(account, investment.get(), difference);
                System.out.println("Ihnen wurde der aktuelle Preis in Höhe von " + (investment.get().getFinancialObject().getIssueprice() * investmentamount) + "€ gutgeschrieben.");
            } else if (difference == 0) {
                bank.sellWholeAmountOfInvestment(account, investment.get(), investmentamount);
                System.out.println("Ihnen wurde der aktuelle Preis in Höhe von " + (investment.get().getFinancialObject().getIssueprice() * investmentamount) + "€ gutgeschrieben.");
            } else if (difference < 0) {
                bank.sellCustomAmountOfInvestment(account, investment.get(), investmentamount);
                if (!financeproduct.equals("Cryptowährung")) {
                    System.out.println("Auf ihrem Depot befinden sich nur " + investment.get().getAmount() + "X " + financeproduct + " || Name: " + investment.get().getFinancialObject().getFinancialObjectname() + " || WKN:" + investment.get().getFinancialObject().getWkn() + " || ISIN:" + investment.get().getFinancialObject().getIsin() + " || zu einem Betrag von " + investment.get().getSum() + "€.");
                } else {
                    System.out.println("Auf ihrem Depot befinden sich nur " + investment.get().getAmount() + "X " + investment.get().getFinancialObject().getFinancialObjectname() + " || ISIN:" + investment.get().getFinancialObject().getIsin() + " || zu einem Betrag von " + investment.get().getSum() + "€.");
                }
            }
            System.out.println("!!! --- Aktie wurde nicht in ihrem Depot gefunden --- !!!");
        }
    }*/

    private  void tradingStocks(long customernumber){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wie wollen sie fortfahren? Um Aktien zu kaufen geben sie \"Kaufen\" ein, um Aktien zu verkaufen \"Verkaufen\"");
        String input = scanner.nextLine().toLowerCase(Locale.ROOT);

        if(input.equals("kaufen")){
            buyfinancialObject(customernumber);
        } else if(input.equals("verkaufen")){
            //sellInvestment();
        } else {
            System.out.println("Eingabe fehlerhaft, bitte wiederholen sie die Eingabe");
            tradingStocks(customernumber);
        }
        logedinAccount(customernumber);
    }
}