import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double count = 1.0;
        String champion = StdIn.readString();
        while (!StdIn.isEmpty()) {
            String newword = StdIn.readString();
            count++;

            boolean sign = StdRandom.bernoulli(1/count);
            if (sign) {
                champion = newword;
            }
        }
        StdOut.println(champion);
    }
}
