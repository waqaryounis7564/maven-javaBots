package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class KR {
    public static void scrapeData() throws IOException {
        List<String> mem = new ArrayList<>();
        mem.add("waqar");
        mem.add("bilal");
        mem.add("waqar younas");
        Predicate<String> p1 = s -> s.contains("ar");
        System.out.println(mem.stream().anyMatch(p1));
    }
}
