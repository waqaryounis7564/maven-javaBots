public class AUSDisclosure {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public String getTotalShortedShares() {
        return totalShortedShares;
    }

    public void setTotalShortedShares(String totalShortedShares) {
        this.totalShortedShares = totalShortedShares;
    }

    public String getTotalShareOutstanding() {
        return totalShareOutstanding;
    }

    public void setTotalShareOutstanding(String totalShareOutstanding) {
        this.totalShareOutstanding = totalShareOutstanding;
    }

    public String getPercentOfTotalShareOutstanding() {
        return percentOfTotalShareOutstanding;
    }

    public void setPercentOfTotalShareOutstanding(String percentOfTotalShareOutstanding) {
        this.percentOfTotalShareOutstanding = percentOfTotalShareOutstanding;
    }

    public String getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(String positionDate) {
        this.positionDate = positionDate;
    }

    private String positionDate;
    private String url;
    private String issuer;
    private String issuerCode;
    private String totalShortedShares;
    private String totalShareOutstanding;
    private String percentOfTotalShareOutstanding;

}

//Product = Issuer
//Product Code = Issuer Code
//Reported Short Positions =  Total Shorted Shares
//Total Product in Issue =  Total Share Outstanding
//% of Total Product in Issue Reported as Short Positions =  % of Total Share Outstanding