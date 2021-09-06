package backend;

import backend.financialobjects.Bond;
import backend.financialobjects.Crypto;
import backend.financialobjects.ETF;
import backend.financialobjects.Stock;

import javax.persistence.*;
import java.util.*;
import java.util.stream.IntStream;

@SuppressWarnings({"java:S106", "java:S6212", "java:S1130", "java:S1319", "java:S3740", "java:S1854", "java:S2259", "java:S3655", "java:S2175"})
@Entity
@Table(name = "Bankaccount")
public class Bank {
    @Column
    private double balance;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int bank_id;

    public Bank(double balance) {
        this.balance = balance;
    }
    public Bank(){

    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
    public int getBank_id() {
        return bank_id;
    }

    public void purchaseFinancialObject(long account, Stock financialObject, double amount){
        EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("AktienTrading");
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            Query query = em.createQuery("SELECT * FROM Depot WHERE customernumber = :stock_id AND amount >= 1");
            query.setParameter("stock_id", financialObject.getStock_id());
            Investment investment = (Investment) query.getSingleResult();
            if(investment.getInvestment_number() > 0) {
                em.createQuery("UPDATE Investments SET amount = :amount AND issueprice = :issueprice WHERE financial_number = :financial_number");
                query.setParameter("amount", investment.getAmount()+amount);
                query.setParameter("issueprice", ((investment.getSum()/investment.getAmount())*(investment.getAmount()+amount)));
                query.setParameter("financial_number", investment.getInvestment_number());
                query.executeUpdate();
            }
        } catch (NoResultException ex) {
            try{
                et = em.getTransaction();
                et.begin();
                em.persist(new Investment(financialObject, amount, financialObject.getIssueprice()*amount));
                CustomerAccount customerAccount = em.find(CustomerAccount.class, 1);
                et.commit();
            } catch (Exception exception){
                if(et != null){
                    et.rollback();
                }
                exception.printStackTrace();
            }
        } finally {
            em.close();
        }
        ENTITY_MANAGER_FACTORY.close();
    }
}