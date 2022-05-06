package patrik.ga.algorithms.geneticalg.selectionalg;

import patrik.ga.Parameters;
import patrik.ga.algorithms.geneticalg.Solution;
import patrik.ga.algorithms.interfaces.ISelection;
import patrik.ga.util.Maximization;

import static patrik.ga.Parameters.populationSize;

public class Tournament implements ISelection {
    private int tournamentSize;

    public Tournament(int tournamentSize){
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Solution select(Solution[] individuals) {
        Solution current = chooseRandomSolution(individuals);
        Solution best = current;

        for (int i = 1; i < tournamentSize ; i++) {
            current = chooseRandomSolution(individuals);

            if(Maximization.better(current.calculateFitness(), best.calculateFitness())){
                best = current;
            }
        }

        return best.myClone();
    }
    private Solution chooseRandomSolution(Solution[] individuals) {

        int randomIndex = (int) (Math.random() * tournamentSize);

        return individuals[randomIndex];
    }
}
