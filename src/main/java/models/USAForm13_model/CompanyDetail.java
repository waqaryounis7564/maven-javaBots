package models.USAForm13_model;

import org.json.JSONArray;

import java.util.List;

public class CompanyDetail {
    private String company_name;
    private String form_type;
    private String source_url;
    private String filing_date;

    public String getTableUrl() {
        return tableUrl;
    }

    public void setTableUrl(String tableUrl) {
        this.tableUrl = tableUrl;
    }

    private String tableUrl;
    private FilingDetail filingDetail;
    private JSONArray informationTables;

    public FilingDetail getFilingDetail() {
        return filingDetail;
    }

    public void setFilingDetail(FilingDetail filingDetail) {
        this.filingDetail = filingDetail;
    }


    public JSONArray getInformationTables() {
        return informationTables;
    }

    public void setInformationTables(JSONArray informationTables) {
        this.informationTables = informationTables;
    }


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getForm_type() {
        return form_type;
    }

    public void setForm_type(String form_type) {
        this.form_type = form_type;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getFiling_date() {
        return filing_date;
    }

    public void setFiling_date(String filing_date) {
        this.filing_date = filing_date;
    }
}
