package backend.financialobjects;
import backend.FinancialObject;

import javax.persistence.*;
import javax.persistence.Column;

@Entity
@Table(name = "Stock")
public class Stock extends FinancialObject {
    public Stock(String stockname, String country, String wkn, String isin, double issueprice) {
        super(stockname, country, wkn, isin, issueprice);
    }
    public String toString(){
        return "leer";
    }
    public Stock(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int stock_id;

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }
    public int getStock_id() {
        return stock_id;
    }

}