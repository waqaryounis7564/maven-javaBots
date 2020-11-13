
import models.swedenModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Testing {
public static void scrapeData() throws IOException {

//    swedenModel a=Stream.of("a","b","c").map(Testing::getmodel).collect(Collectors.toMap());
}
public static swedenModel getmodel(String a){
    swedenModel s=new swedenModel();
    s.setIssuerName("waqar");
    s.setSourceURL(a);
    return s;
}

}





