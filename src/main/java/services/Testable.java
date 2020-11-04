package services;


import java.util.stream.Stream;

public class Testable {
    public static void scrape() {
     Stream.of("a","b","c").forEach(Testable::move);
    }

    public static void move(String a){
        System.out.println(a);
        for(int i=0;i<5;i++){
            System.out.println(i);
        }

    }
}
