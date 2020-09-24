package models;

import java.math.BigInteger;

public class Otc_USA_Disclosure {
    private String  date;
    private String ticker;
    private BigInteger shares_Shorted_Volume;
    private BigInteger short_Exempt_Volume;
    private BigInteger total_Volume;
    private String  market;
    private String  url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigInteger getShares_Shorted_Volume() {
        return shares_Shorted_Volume;
    }

    public void setShares_Shorted_Volume(BigInteger shares_Shorted_Volume) {
        this.shares_Shorted_Volume = shares_Shorted_Volume;
    }

    public BigInteger getShort_Exempt_Volume() {
        return short_Exempt_Volume;
    }

    public void setShort_Exempt_Volume(BigInteger short_Exempt_Volume) {
        this.short_Exempt_Volume = short_Exempt_Volume;
    }

    public BigInteger getTotal_Volume() {
        return total_Volume;
    }

    public void setTotal_Volume(BigInteger total_Volume) {
        this.total_Volume = total_Volume;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
