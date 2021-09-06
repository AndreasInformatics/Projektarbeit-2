package backend;
import backend.financialobjects.Bond;
import backend.financialobjects.Crypto;
import backend.financialobjects.ETF;
import backend.financialobjects.Stock;

import java.util.List;

@SuppressWarnings("java:S3740")
public class Market {
    private List<Stock> stocklist;
    private List<ETF> etflist;
    private List<Crypto> cryptolist;
    private List<Bond> bondslist;

    public Market(List<Stock> stocklist, List<ETF> etflist, List<Crypto> cryptolist, List<Bond> bondslist){
        this.stocklist = stocklist;
        this.etflist = etflist;
        this.cryptolist = cryptolist;
        this.bondslist = bondslist;
    }

    public List<Stock> getStocklist(){
        return stocklist;
    }
    public List<ETF> getEtflist(){
        return etflist;
    }
    public List<Crypto> getCryptolist(){
        return cryptolist;
    }
    public List<Bond> getBondslist(){
        return bondslist;
    }
}
