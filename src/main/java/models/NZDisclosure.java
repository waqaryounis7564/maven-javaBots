package models;

public class NZDisclosure {
    private String headline;
    private String ticker;

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getAnnsUrl() {
        return annsUrl;
    }

    public void setAnnsUrl(String annsUrl) {
        this.annsUrl = annsUrl;
    }

    private String releasedDate;
    private String annsUrl;
}
