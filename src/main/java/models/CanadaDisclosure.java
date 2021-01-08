package models;

import java.math.BigDecimal;

public class CanadaDisclosure {
    private String reporting_date;
    private String position_date;
    private String ticker;
    private String issuer_name;
    private String market;
    private String short_selling_volume; //
    private String  percent_of_total_traded_volume;
    private String  short_sell_traded_value;

    public String getReporting_date() {
        return reporting_date;
    }

    public void setReporting_date(String reporting_date) {
        this.reporting_date = reporting_date;
    }

    public String getPosition_date() {
        return position_date;
    }

    public void setPosition_date(String position_date) {
        this.position_date = position_date;
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

    public String getShort_selling_volume() {
        return short_selling_volume;
    }

    public void setShort_selling_volume(String short_selling_volume) {
        this.short_selling_volume = short_selling_volume;
    }

    public String getPercent_of_total_traded_volume() {
        return percent_of_total_traded_volume;
    }

    public void setPercent_of_total_traded_volume(String percent_of_total_traded_volume) {
        this.percent_of_total_traded_volume = percent_of_total_traded_volume;
    }

    public String getShort_sell_traded_value() {
        return short_sell_traded_value;
    }

    public void setShort_sell_traded_value(String short_sell_traded_value) {
        this.short_sell_traded_value = short_sell_traded_value;
    }

    public String getPercent_of_total_traded_value() {
        return percent_of_total_traded_value;
    }

    public void setPercent_of_total_traded_value(String percent_of_total_traded_value) {
        this.percent_of_total_traded_value = percent_of_total_traded_value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShort_sale_trades() {
        return short_sale_trades;
    }

    public void setShort_sale_trades(String short_sale_trades) {
        this.short_sale_trades = short_sale_trades;
    }

    public String getPercent_of_total_trades() {
        return percent_of_total_trades;
    }

    public void setPercent_of_total_trades(String percent_of_total_trades) {
        this.percent_of_total_trades = percent_of_total_trades;
    }

    private String  percent_of_total_traded_value;
    private String url;
    private String  short_sale_trades;
    private String  percent_of_total_trades;



}
