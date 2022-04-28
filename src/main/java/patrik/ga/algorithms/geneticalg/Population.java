package patrik.ga.algorithms.geneticalg;

import patrik.ga.Parameters;
import patrik.ga.util.Maximization;

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




    // selection


    public Solution selection(){

        return proportionalSelection();
    }


    public Solution tournamentSelection(){

        Solution current = chooseRandomSolution();
        Solution best = current;


        for (int i = 1; i <Parameters.tournamentSize ; i++) {
           current = chooseRandomSolution();

           if(Maximization.better(current.calculateFitness(), best.calculateFitness())){
               best = current;
           }
        }

        return best.myClone();

    }
    public Solution proportionalSelection(){
        double total = 0;
        for (var sol: this.individuals) {
            total += sol.calculateFitness();
        }
        double rand = new Random().nextDouble(0, total);
        double partialSum = 0.0;
        for(var sol : this.individuals){
            partialSum += sol.calculateFitness();
            if(partialSum <= rand){
                return sol;
            }
        }
        return new Solution(1,1);
    }

    private Solution chooseRandomSolution() {

        int randomIndex = (int) (Math.random() * Parameters.populationSize);

        return individuals[randomIndex];
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
