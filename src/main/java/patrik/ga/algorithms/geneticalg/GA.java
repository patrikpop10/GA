package patrik.ga.algorithms.geneticalg;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import patrik.ga.Parameters;
import patrik.ga.algorithms.interfaces.IAlgorithms;
import patrik.ga.controllers.MainController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GA implements IAlgorithms {
    /*---------------------------------------------------------------
     *
     *    main
     *
     *      This method contains a high-level implementation
     *      of the Simulated annealing algorithm.
     *
     */





    public void run() throws InterruptedException {

        Population pop = new Population();
        Solution best = pop.bestOfPop();
        System.out.print("1.       ");

        for (int gen = 1; gen < Parameters.maxNumberOfGenerations; gen++) {

            Population newPop = new Population();
            int currentNumberOfIndividuals = 0;


            while(currentNumberOfIndividuals < Parameters.populationSize){

                //initialization
                if(currentNumberOfIndividuals == 0){
                    newPop.individuals[currentNumberOfIndividuals] = best.myClone();
                    currentNumberOfIndividuals++;


                    if(Parameters.populationSize % 2 == 0){
                        Solution s = pop.selection();
                        newPop.individuals[currentNumberOfIndividuals] = s.myClone().mutation();
                        currentNumberOfIndividuals++;
                    }





                }//ends initizialition

                else {


                    Solution dad = pop.selection();
                    pop.individuals = ArrayUtils.removeElement(pop.individuals,dad);
                    Solution mom = pop.selection();
                    pop.individuals = ArrayUtils.removeElement(pop.individuals, mom);

                    Solution child1 = new Solution(dad.getWidth(), dad.getHeight());
                    Solution child2 = new Solution(dad.getWidth(), dad.getHeight());


                    if (Math.random() < Parameters.crossoverRate) {
                        crossover(dad, mom, child1, child2);
                    }else {
                        child1 = dad.myClone();
                        child2 = mom.myClone();
                    }


                    newPop.individuals[currentNumberOfIndividuals] = child1.myClone().mutation();
                    currentNumberOfIndividuals++;

                    newPop.individuals[currentNumberOfIndividuals] = child2.myClone().mutation();
                    currentNumberOfIndividuals++;


                }


            }//ends for
            pop = newPop;
            best = pop.bestOfPop();

            if(gen % 100 == 0 || gen == 1){
                BufferedImage bufferedImage = new BufferedImage((int) best.getWidth(), (int) best.getHeight(), BufferedImage.TYPE_INT_RGB);
                int[] arr = best.getColors().stream().mapToInt(i -> i.getRGB()).toArray();
                bufferedImage.setRGB(0,0,(int) best.getWidth(),(int) best.getHeight(), arr, 0,(int) best.getWidth());
                MainController.context.drawImage(SwingFXUtils.toFXImage(bufferedImage,null), 0, 0);
            }
            System.out.print(gen+1 +".   " + best.calculateFitness());
            System.out.println();

        }



    }  // terminates the main method

    //crossover
    //TODO: CHANGE FOR A MORE RANDOM APPROACH

    private static void crossover(Solution father, Solution mother, Solution child1, Solution child2){
        var genePool =  Stream.concat(father.getShapes().stream(), mother.getShapes().stream()).collect(Collectors.toList());
        Collections.shuffle(genePool);
        var childOneGenes = new ArrayList<patrik.ga.util.image.Shape>();
        var childTwoGenes = new ArrayList<patrik.ga.util.image.Shape>();
        int randomIndex = (int) (new Random().nextDouble(0,1) *(genePool.size() -1));

       while(randomIndex == 0 )
       {

           randomIndex = (int) (new Random().nextDouble(0.1,1) *(genePool.size() -1));
       }
        for (int i = 0; i <=randomIndex ; i++) {

           childOneGenes.add(genePool.get(i));

        }

        for (int i = randomIndex+1; i < genePool.size()  ; i++) {
            childTwoGenes.add(genePool.get(i));

        }
        if(childOneGenes.size() == 0 || childTwoGenes.size() == 0)
        {
            System.out.println("here");
        }
        child1.setShapes(childOneGenes);
        child2.setShapes(childTwoGenes);

    }

}
