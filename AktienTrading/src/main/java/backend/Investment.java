package backend;

import backend.financialobjects.Stock;

import javax.persistence.*;

@Entity
@Table(name = "Investments")
public class Investment{
    @Column
    private double amount;
    @Column
    private double sum;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int investment_number;

    @Transient
    private FinancialObject financialObject;
    public FinancialObject getFinancialObject() {
        return financialObject;
    }

    public Investment(){
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }
    public void setInvestment_number(int investment_number) {
        this.investment_number = investment_number;
    }

    public double getAmount() {
        return amount;
    }
    public double getSum() {
        return sum;
    }
    public int getInvestment_number() {
        return investment_number;
    }

    public Investment(FinancialObject financialObject, double amount, double sum) {
        this.financialObject = financialObject;
        this.amount = amount;
        this.sum = sum;
    }
}