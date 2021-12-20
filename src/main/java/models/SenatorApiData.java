package models;

public class SenatorApiData {
  private int congressNumber;
  private String chamber;
  private String memberId;
  private String jsonData;

  public int getCongressNumber() {
    return congressNumber;
  }

  public void setCongressNumber(int congressNumber) {
    this.congressNumber = congressNumber;
  }

  public String getChamber() {
    return chamber;
  }

  public void setChamber(String chamber) {
    this.chamber = chamber;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getJsonData() {
    return jsonData;
  }

  public void setJsonData(String jsonData) {
    this.jsonData = jsonData;
  }
}
