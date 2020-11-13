package models;

import java.math.BigDecimal;

public class swedenModel {
    public String getPositionHolder() {
        return positionHolder;
    }

    public void setPositionHolder(String positionHolder) {
        this.positionHolder = positionHolder;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getIsinCode(String az) {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public BigDecimal getNetShortPosition() {
        return netShortPosition;
    }

    public void setNetShortPosition(BigDecimal netShortPosition) {
        this.netShortPosition = netShortPosition;
    }

    public String getPositionDate() {
        return positionDate;
    }

    public void setPositionDate(String positionDate) {
        this.positionDate = positionDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    private String positionHolder;
    private String issuerName;
    private String isinCode;
    private BigDecimal netShortPosition;
    private String positionDate;
    private String publicationDate;
    private String sourceURL;

}
