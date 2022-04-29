package patrik.ga;

import patrik.ga.util.image.Image;

public class Parameters {

    public static boolean maximization = false; //it's a minimization problem
    public static Image BaseImage;//stores the values of the problem
    public static int imageSize = 200;
    public static int numberOfCircles = 100;
    public static int maxCircleSize =60;
    //Parameters for the GA
    public static int populationSize = 20;
    public static int maxNumberOfGenerations = 30000;
    public static int tournamentSize = 4;
    public static double crossoverRate = 0.8;
    public static double mutationRate = 0.1;

}
