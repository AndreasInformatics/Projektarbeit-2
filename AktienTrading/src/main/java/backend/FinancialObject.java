package backend;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public class FinancialObject implements Serializable {
    @Column(name = "name", nullable = false)
    private String financialObjectname;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String wkn;
    @Column(nullable = false)
    private String isin;
    @Column(nullable = false)
    private double issueprice;

    @Transient
    private FinancialObject financialObject;
    public FinancialObject getFinancialObject() {
        return financialObject;
    }

    public FinancialObject(){
    }

    public void setFinancialObjectname(String financialObjectname) {
        this.financialObjectname = financialObjectname;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setWkn(String wkn) {
        this.wkn = wkn;
    }
    public void setIsin(String isin) {
        this.isin = isin;
    }
    public void setIssueprice(double issueprice) {
        this.issueprice = issueprice;
    }

    public String getFinancialObjectname() {
        return financialObjectname;
    }
    public String getCountry() {
        return country;
    }
    public String getWkn() {
        return wkn;
    }
    public String getIsin() {
        return isin;
    }
    public double getIssueprice() {
        return issueprice;
    }

    public FinancialObject(String financialObjectname, String country, String wkn, String isin, double issueprice) {
        this.financialObjectname = financialObjectname;
        this.country = country;
        this.wkn = wkn;
        this.isin = isin;
        this.issueprice = issueprice;
    }

    public FinancialObject(String financialObjectname, String wkn, String isin, double issueprice) {
        this.financialObjectname = financialObjectname;
        this.wkn = wkn;
        this.isin = isin;
        this.issueprice = issueprice;
    }

    public FinancialObject(String financialObjectname, double issueprice) {
        this.financialObjectname = financialObjectname;
        this.issueprice = issueprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialObject that = (FinancialObject) o;
        return Double.compare(that.issueprice, issueprice) == 0 && Objects.equals(financialObjectname, that.financialObjectname) && Objects.equals(country, that.country) && Objects.equals(wkn, that.wkn) && Objects.equals(isin, that.isin) && Objects.equals(financialObject, that.financialObject);
    }
}
