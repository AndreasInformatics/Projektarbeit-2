package backend.financialobjects;
import backend.FinancialObject;

public class Bond extends FinancialObject {
    public Bond(String bondname, String country, String wkn, String isin, double issueprice) {
        super(bondname, country, wkn, isin, issueprice);
    }
    public String toString(){
        return "leer";
    }
}
