package services.SDS;

public class OtcCandaDisclosure {
    private String position_date, reporting_date, issuer_name, ticker, market, no_of_shorted_shares_position, net_change, url;

    public String getPosition_date() {
        return position_date;
    }

    public void setPosition_date(String position_date) {
        this.position_date = position_date;
    }

    public String getReporting_date() {
        return reporting_date;
    }

    public void setReporting_date(String reporting_date) {
        this.reporting_date = reporting_date;
    }

    public String getIssuer_name() {
        return issuer_name;
    }

    public void setIssuer_name(String issuer_name) {
        this.issuer_name = issuer_name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getNo_of_shorted_shares_position() {
        return no_of_shorted_shares_position;
    }

    public void setNo_of_shorted_shares_position(String no_of_shorted_shares_position) {
        this.no_of_shorted_shares_position = no_of_shorted_shares_position;
    }

    public String getNet_change() {
        return net_change;
    }

    public void setNet_change(String net_change) {
        this.net_change = net_change;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
