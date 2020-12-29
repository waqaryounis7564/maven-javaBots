package models.sds;

public class KoreaDisclosure {
  private String  positionDate;
  private String  reportingDate;
  private String isin;
  private String issuerName;
  private String shareShortedVolume;
  private String shareOutstanding;
  private String shareShortedValue;
  private String marketCap;
  private String shortPositionPercentage;
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPositionDate() {
    return positionDate;
  }

  public void setPositionDate(String positionDate) {
    this.positionDate = positionDate;
  }

  public String getReportingDate() {
    return reportingDate;
  }

  public void setReportingDate(String reportingDate) {
    this.reportingDate = reportingDate;
  }

  public String getIsin() {
    return isin;
  }

  public void setIsin(String isin) {
    this.isin = isin;
  }

  public String getIssuerName() {
    return issuerName;
  }

  public void setIssuerName(String issuerName) {
    this.issuerName = issuerName;
  }

  public String getShareShortedVolume() {
    return shareShortedVolume;
  }

  public void setShareShortedVolume(String shareShortedVolume) {
    this.shareShortedVolume = shareShortedVolume;
  }

  public String getShareOutstanding() {
    return shareOutstanding;
  }

  public void setShareOutstanding(String shareOutstanding) {
    this.shareOutstanding = shareOutstanding;
  }

  public String getShareShortedValue() {
    return shareShortedValue;
  }

  public void setShareShortedValue(String shareShortedValue) {
    this.shareShortedValue = shareShortedValue;
  }

  public String getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(String marketCap) {
    this.marketCap = marketCap;
  }

  public String getShortPositionPercentage() {
    return shortPositionPercentage;
  }

  public void setShortPositionPercentage(String shortPositionPercentage) {
    this.shortPositionPercentage = shortPositionPercentage;
  }
}
