package services;

import java.io.*;
import java.util.Scanner;

public class Testable {

    public static void scrape()  {
        int a = 1;
        int b = 1;
        for (int i = 2; i < 10; i++) {
            int temp=a;
            a += b;
            b = temp;
            int c=0;
        }
        System.out.println(a);
    }

}




