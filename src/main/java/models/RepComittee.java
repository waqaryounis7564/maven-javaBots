package models;

public class RepComittee {
  String committee_name;
  String  member_name;
  String designation;
  String state;
  String party;
  String congress_type;
  String source_url;
  String image_url;

  public String getRank() {
    return rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }

  String rank;

  public String getCommittee_name() {
    return committee_name;
  }

  public void setCommittee_name(String committee_name) {
    this.committee_name = committee_name;
  }

  public String getMember_name() {
    return member_name;
  }

  public void setMember_name(String member_name) {
    this.member_name = member_name;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getParty() {
    return party;
  }

  public void setParty(String party) {
    this.party = party;
  }

  public String getCongress_type() {
    return congress_type;
  }

  public void setCongress_type(String congress_type) {
    this.congress_type = congress_type;
  }

  public String getSource_url() {
    return source_url;
  }

  public void setSource_url(String source_url) {
    this.source_url = source_url;
  }

  public String getImage_url() {
    return image_url;
  }

  public void setImage_url(String image_url) {
    this.image_url = image_url;
  }
}
