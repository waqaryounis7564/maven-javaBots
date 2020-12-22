package models.USAForm13_model;

public class InformationTable {

  private int form_id;
  private String  table_url;
  private String  name_of_issuer;
  private String title_of_class;
  private String cusip;
  private String value;
  private String sshprnamt;
  private String sshprnamt_type;
  private String call;
  private String investment_discretion;
  private String other_manager;
  private String sole;
  private String shared;

  public int getForm_id() {
    return form_id;
  }

  public void setForm_id(int form_id) {
    this.form_id = form_id;
  }

  public String getTable_url() {
    return table_url;
  }

  public void setTable_url(String table_url) {
    this.table_url = table_url;
  }

  public String getName_of_issuer() {
    return name_of_issuer;
  }

  public void setName_of_issuer(String name_of_issuer) {
    this.name_of_issuer = name_of_issuer;
  }

  public String getTitle_of_class() {
    return title_of_class;
  }

  public void setTitle_of_class(String title_of_class) {
    this.title_of_class = title_of_class;
  }

  public String getCusip() {
    return cusip;
  }

  public void setCusip(String cusip) {
    this.cusip = cusip;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getSshprnamt() {
    return sshprnamt;
  }

  public void setSshprnamt(String sshprnamt) {
    this.sshprnamt = sshprnamt;
  }

  public String getSshprnamt_type() {
    return sshprnamt_type;
  }

  public void setSshprnamt_type(String sshprnamt_type) {
    this.sshprnamt_type = sshprnamt_type;
  }

  public String getCall() {
    return call;
  }

  public void setCall(String call) {
    this.call = call;
  }

  public String getInvestment_discretion() {
    return investment_discretion;
  }

  public void setInvestment_discretion(String investment_discretion) {
    this.investment_discretion = investment_discretion;
  }

  public String getOther_manager() {
    return other_manager;
  }

  public void setOther_manager(String other_manager) {
    this.other_manager = other_manager;
  }

  public String getSole() {
    return sole;
  }

  public void setSole(String sole) {
    this.sole = sole;
  }

  public String getShared() {
    return shared;
  }

  public void setShared(String shared) {
    this.shared = shared;
  }

  public String getNone() {
    return none;
  }

  public void setNone(String none) {
    this.none = none;
  }

  private String none;

}
