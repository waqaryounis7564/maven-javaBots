package services;

public class Testable {
    public static void show(int num) {
        num += 2;
        System.out.println(num);
    }

    public static void scrape() {
        try {
            int res = 5 / 5;
            for (int i = 0; i <= 15; i++) {
                res = i;
                show(res);
            }
            System.out.println(res);

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
