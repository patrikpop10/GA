package patrik.ga.algorithms.geneticalg.crossover;

import patrik.ga.Parameters;
import patrik.ga.algorithms.geneticalg.Solution;
import patrik.ga.algorithms.interfaces.ICrossover;

import java.util.*;
import java.util.stream.Collectors;

public class KPointCrossover implements ICrossover {
    private int numberOfCutOutPoints;
    public KPointCrossover(int numberOfCutOutPoints){
        this.numberOfCutOutPoints = numberOfCutOutPoints;
    }
    @Override
    public void cross(Solution father, Solution mother, Solution son, Solution daughter) {

        do{
            ArrayList<Integer> points = generateCrossoverPoints();

            for (var index : points) {
                var firstNElementsFather = father.getColors().stream().limit(index).collect(Collectors.toList());
                var firstNElementsMother = mother.getColors().stream().limit(index).collect(Collectors.toList());
                son.getColors().addAll(firstNElementsFather);
            }





            int randomIndex = (int) (Math.random() *(Parameters.BaseImage.getColors().size() -1));
            for (int i = 0; i <=randomIndex ; i++) {
                son.getColors().add(i, father.getColors().get(i));
                daughter.getColors().add(i, mother.getColors().get(i));
            }

            for (int i = randomIndex+1; i < Parameters.BaseImage.getColors().size()  ; i++) {
                son.getColors().add(i, mother.getColors().get(i));
                daughter.getColors().add(i, father.getColors().get(i));
            }
        } while(son.getColors().equals(father.getColors()) ||
                son.getColors().equals(mother.getColors()) ||
                daughter.getColors().equals(father) ||
                daughter.getColors().equals(mother));

    }
    private ArrayList<Integer> generateCrossoverPoints(){
        ArrayList<Integer> crossOverPoints = new ArrayList<>();
        Set s = new HashSet(crossOverPoints);

        do{
            for (int i = 0; i <numberOfCutOutPoints ; i++) {
                int randomIndex = (int) (Math.random() *(Parameters.BaseImage.getColors().size() -1));
                crossOverPoints.add(randomIndex);
            }
            s.clear();
            s = new HashSet(crossOverPoints);
        }
        while(s.size() != crossOverPoints.size());
        Collections.sort(crossOverPoints);
        return crossOverPoints;
    }
}
