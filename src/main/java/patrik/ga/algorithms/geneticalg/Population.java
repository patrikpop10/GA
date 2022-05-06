package patrik.ga.algorithms.geneticalg;

import patrik.ga.Parameters;
import patrik.ga.algorithms.interfaces.ISelection;
import patrik.ga.util.Maximization;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Population {


    public Solution[] individuals;


    //Constructor

    public Population(){

        individuals = new Solution[Parameters.populationSize];

        for (int i = 0; i < Parameters.populationSize ; i++) {
            individuals[i] = new Solution(Parameters.BaseImage.getWidth(), Parameters.BaseImage.getHeight());

        }


    }
    public Solution GetNthSolution(int solNumber){
        return Arrays.stream(this.individuals).sorted(Comparator.comparingDouble(Solution::calculateFitness).reversed()).limit(solNumber).skip(solNumber - 1).findFirst().get();
    }


    // selection

    public Solution selection(ISelection selectionAlg){

        return selectionAlg.select(this.individuals);
    }



    public Solution bestOfPop(){

        Solution current = individuals[0];
        Solution best = current;

        for (Solution Solution : individuals) {

            if(Maximization.better(Solution.calculateFitness(), best.calculateFitness())){
                    best = Solution;
            }
        }

        return best.myClone();



    }
}
