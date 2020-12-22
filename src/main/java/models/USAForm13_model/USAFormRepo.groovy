package models.USAForm13_model


import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Slf4j
@Repository
class USAFormRepo {
  private final JdbcTemplate jdbcTemplate

  @Autowired
  USAFormRepo(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate
  }

  void insert(CompanyDetail companyDetail) throws EmptyResultDataAccessException {
    String companyQuery = """INSERT INTO "USA_Form_13f"
                            (company_name,form_type,source_url,filing_date
                        ) VALUES
                             (?, ?, ?,
                             ?::date
                             ) ON CONFLICT DO NOTHING;"""

    Integer filingId = jdbcTemplate.queryForObject(companyQuery, Integer.class,
        companyDetail.companyName,
        companyDetail.formType,
        companyDetail.sourceUrl,
        companyDetail.filingDate
    )
    String filingQuery = """INSERT INTO sbb."USA_form_filingDetail" (
form_id,cik_number,report_date,accepted_date,effectivness_date)
                                                             VALUES( ?, ?, ?
                                                                    ,?, ?::date, ?
                                                                    ,?, ?
                                                                    ,?, ?
                                                                    ,?, ?)
                                                             ON CONFLICT DO NOTHING;"""
    companyDetail.senatorTradesList.each {
      jdbcTemplate.update(filingQuery,
          filingId
          , it.form_id
          , it.cik_number
          , it.report_date
          , it.accepted_date
          , it.effectivness_date
      )
    }
    companyDetail.jpgLinks.each {
      jdbcTemplate.update('INSERT INTO "SenatorTradesImages" ("filingId", "jpgLink") values (?, ?) ON CONFLICT DO NOTHING;', filingId, it)
    }
  }


}
