package patrik.ga;

import patrik.ga.util.image.Image;

public class Parameters {

    public static boolean maximization = false; //it's a minimization problem
    public static Image BaseImage;//stores the values of the problem
    //Parameters for the GA
    public static int imageSize = 150;
    public static int populationSize = 111;
    public static int maxNumberOfGenerations = 100000000;
    public static int tournamentSize = 3;
    public static double crossoverRate = 0.9;
    public static double mutationRate = 0.001;

}
