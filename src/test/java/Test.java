public class Test {

    public static void main(String[] args) {
        int n = 17;
        double p1 = 0.2;
        double p2 = 0.5;
        double res = 0;
        res = p1 * Math.pow(1 - p1, n - 1)*p2*n + Math.pow(1-p1, n) * p2;
        System.out.println(res);
    }


}
