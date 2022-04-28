package patrik.ga.util;


import patrik.ga.Parameters;

public class Maximization {

    public static boolean better(double x, double y) {

        if (Parameters.maximization) {
            return (x > y);
        } else {
            return (x < y);
        }
    } // terminates the method better

}
