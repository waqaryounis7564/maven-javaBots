package models.USAForm13_model;

public class FilingDetail {

  public String getCik_number() {
    return cik_number;
  }

  public void setCik_number(String cik_number) {
    this.cik_number = cik_number;
  }

  public String getReport_date() {
    return report_date;
  }

  public void setReport_date(String report_date) {
    this.report_date = report_date;
  }

  public String getAccepted_date() {
    return accepted_date;
  }

  public void setAccepted_date(String accepted_date) {
    this.accepted_date = accepted_date;
  }

  public String getEffectivness_date() {
    return effectivness_date;
  }

  public void setEffectivness_date(String effectivness_date) {
    this.effectivness_date = effectivness_date;
  }

  private String  cik_number;
  private String  report_date;
  private String  accepted_date;
  private String  effectivness_date;
}
