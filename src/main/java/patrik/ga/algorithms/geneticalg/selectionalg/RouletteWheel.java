package patrik.ga.algorithms.geneticalg.selectionalg;

import patrik.ga.algorithms.geneticalg.Solution;
import patrik.ga.algorithms.interfaces.ISelection;

import java.util.Random;

public class RouletteWheel implements ISelection {
    @Override
    public Solution select(Solution[] individuals) {
        double total = 0;
        for (var sol: individuals) {
            total += sol.calculateFitness();
        }
        while(true){
            double rand = new Random().nextDouble(0, total);
            double partialSum = 0.0;
            for(var sol : individuals){
                partialSum += sol.calculateFitness();
                if(partialSum <= rand){
                    return sol;
                }
            }
        }

    }
}
