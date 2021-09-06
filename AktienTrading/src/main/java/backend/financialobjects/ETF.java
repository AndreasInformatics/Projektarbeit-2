package backend.financialobjects;
import backend.FinancialObject;

public class ETF extends FinancialObject {
    public ETF(String etfname, String wkn, String isin, double issueprice) {
        super(etfname, wkn, isin, issueprice);
    }
    public String toString(){
        return "leer";
    }
}
