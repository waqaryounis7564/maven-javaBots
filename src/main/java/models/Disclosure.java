package models;

public class Disclosure {
    private String date;
    private int stockNumber;
    private String issuerName;
    private String aggregatedShortedShares;
    private String valueOfShortedShares;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getAggregatedShortedShares() {
        return aggregatedShortedShares;
    }

    public void setAggregatedShortedShares(String aggregatedShortedShares) {
        this.aggregatedShortedShares = aggregatedShortedShares;
    }

    public String getValueOfShortedShares() {
        return valueOfShortedShares;
    }

    public void setValueOfShortedShares(String valueOfShortedShares) {
        this.valueOfShortedShares = valueOfShortedShares;
    }
}
