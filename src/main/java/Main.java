import services.BR;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class Main {

    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

//        BR.scrapeDate();
//        BR.scrape();
//        BR.scrapeDate();
        BR.scraping();

    }
}
