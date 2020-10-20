import services.BR;
import services.Romania;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class Main {

    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        Romania.scrapeData();
//        BR.scrapeDate();
//        BR.scrape();
//        BR.scrapeDate();
//        BR.scraping();

    }
}
