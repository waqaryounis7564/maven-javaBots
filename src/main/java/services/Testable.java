package services;

public class Testable {
    public static int show(int num) {
      return  num += 2;
//        System.out.println(num);
    }

    public static void scrape() {
        try {
            int res = 5 / 5;
            for (int i = 0; i <= 15; i++) {
                res = i;
                show(res);
                String s="sss";
            }
            System.out.println(show(res));

        } catch (IndexOutOfBoundsException | ArithmeticException ex) {

//            System.out.println(ex.toString());
//            ex.printStackTrace();
//            ex.getMessage();
            System.out.println(ex.getCause());
//            System.out.println(ex.initCause());
            ex.initCause(new ArithmeticException());
        }

    }
}
