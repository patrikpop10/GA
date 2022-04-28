package patrik.ga.algorithms.geneticalg;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import patrik.ga.Parameters;
import patrik.ga.algorithms.interfaces.IAlgorithms;
import patrik.ga.controllers.MainController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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





                }//ends inizialitation

                else {

                    Solution dad = pop.selection();
                    Solution mom = pop.selection();

                    Solution child1 = new Solution(dad.getWidth(), dad.getHeight(), new ArrayList<Color>());
                    Solution child2 = new Solution(dad.getWidth(), dad.getHeight(), new ArrayList<Color>());

                    currentNumberOfIndividuals = elitism(pop, newPop, currentNumberOfIndividuals);

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
            BufferedImage bufferedImage = new BufferedImage((int) best.getWidth(), (int) best.getHeight(), BufferedImage.TYPE_INT_RGB);

            int[] arr = best.getColors().stream().mapToInt(i -> i.getRGB()).toArray();
            bufferedImage.setRGB(0,0,(int) best.getWidth(),(int) best.getHeight(), arr, 0,(int) best.getWidth());
            MainController.context.drawImage(SwingFXUtils.toFXImage(bufferedImage,null), 0, 0);
            Thread.sleep(300);
            System.out.print(gen+1 +".   " + best.calculateFitness());
            System.out.println();
        }



    }  // terminates the main method

    private int elitism(Population pop, Population newPop, int currentNumberOfIndividuals) {
        newPop.individuals[currentNumberOfIndividuals] = pop.bestOfPop().myClone();
        currentNumberOfIndividuals++;
        return currentNumberOfIndividuals;
    }

    //crossover

    private static void crossover(Solution father, Solution mother, Solution child1, Solution child2){

        int randomIndex = (int) (Math.random() *(Parameters.BaseImage.getColors().size() -1));

        for (int i = 0; i <=randomIndex ; i++) {
            child1.getColors().add(i, father.getColors().get(i));
            child2.getColors().add(i, mother.getColors().get(i));

        }

        for (int i = randomIndex+1; i < Parameters.BaseImage.getColors().size()  ; i++) {
            child1.getColors().add(i, mother.getColors().get(i));
            child2.getColors().add(i, father.getColors().get(i));
        }

    }

}
