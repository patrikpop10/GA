package patrik.ga;

import patrik.ga.util.image.Image;

public class Parameters {

    public static boolean maximization = false; //it's a minimization problem
    public static Image BaseImage;//stores the values of the problem
    public static int imageSize = 200;
    public static int numberOfCircles = 900;
    //Parameters for the GA
    public static int populationSize = 6;
    public static int maxNumberOfGenerations = 30000;
    public static int tournamentSize = 2;
    public static double crossoverRate = 0.9;
    public static double mutationRate = 0.25;

}
