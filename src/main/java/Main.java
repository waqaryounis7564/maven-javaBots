import services.sbb.Japan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {

        try(BufferedReader csvReader = new BufferedReader(new FileReader("src/main/static/zillow.csv"))){
            String line;
            csvReader.readLine(); // skipping headers
            while ((line=csvReader.readLine())!=null){
                System.out.println(line);
            }
        }

//                Japan.scrape();

    }


}
