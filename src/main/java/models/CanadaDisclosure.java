package models;

import java.math.BigDecimal;

public class CanadaDisclosure {
    private String reporting_date;

    public String getPosition_date() {
        return position_date;
    }

    public void setPosition_date(String position_date) {
        this.position_date = position_date;
    }

    private String position_date;
    private String ticker;
    private String issuer_name;
    private String market;
    private Long short_selling_volume; //
    private BigDecimal percent_of_total_traded_volume;
    private Long short_sell_traded_value;
    private BigDecimal percent_of_total_traded_value;
    private String url;
    private Long short_sale_trades;
    private BigDecimal percent_of_total_trades;

    public Long getShort_sale_trades() {
        return short_sale_trades;
    }

    public void setShort_sale_trades(Long short_sale_trades) {
        this.short_sale_trades = short_sale_trades;
    }

    public BigDecimal getPercent_of_total_trades() {
        return percent_of_total_trades;
    }

    public void setPercent_of_total_trades(BigDecimal percent_of_total_trades) {
        this.percent_of_total_trades = percent_of_total_trades;
    }

    public String getReporting_date() {
        return reporting_date;
    }

    public void setReporting_date(String reporting_date) {
        this.reporting_date = reporting_date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIssuer_name() {
        return issuer_name;
    }

    public void setIssuer_name(String issuer_name) {
        this.issuer_name = issuer_name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }


    public Long getShort_selling_volume() {
        return short_selling_volume;
    }

    public void setShort_selling_volume(Long short_selling_volume) {
        this.short_selling_volume = short_selling_volume;
    }

    public BigDecimal getPercent_of_total_traded_volume() {
        return percent_of_total_traded_volume;
    }

    public void setPercent_of_total_traded_volume(BigDecimal percent_of_total_traded_volume) {
        this.percent_of_total_traded_volume = percent_of_total_traded_volume;
    }

    public Long getShort_sell_traded_value() {
        return short_sell_traded_value;
    }

    public void setShort_sell_traded_value(Long short_sell_traded_value) {
        this.short_sell_traded_value = short_sell_traded_value;
    }

    public BigDecimal getPercent_of_total_traded_value() {
        return percent_of_total_traded_value;
    }

    public void setPercent_of_total_traded_value(BigDecimal percent_of_total_traded_value) {
        this.percent_of_total_traded_value = percent_of_total_traded_value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
