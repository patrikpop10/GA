package patrik.ga.algorithms.geneticalg;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import patrik.ga.Parameters;
import patrik.ga.algorithms.geneticalg.selectionalg.RouletteWheel;
import patrik.ga.algorithms.geneticalg.selectionalg.SelectionEnum;
import patrik.ga.algorithms.geneticalg.selectionalg.Tournament;
import patrik.ga.algorithms.interfaces.IAlgorithms;
import patrik.ga.algorithms.interfaces.ISelection;
import patrik.ga.controllers.MainController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class GA implements IAlgorithms {
    /*---------------------------------------------------------------
     *
     *    main
     *
     *      This method contains a high-level implementation
     *      of the Simulated annealing algorithm.
     *
     */


    public void run(){
        try{
            Population pop = new Population();
            Solution best = pop.bestOfPop();
            System.out.print("1.       ");
            //int gen = 1;
            //while (true){
            ISelection selection = pickSelectionAlgorithm(SelectionEnum.Tournament);
            for (int gen = 1; gen < Parameters.maxNumberOfGenerations; gen++) {

                Population newPop = new Population();
                int currentNumberOfIndividuals = 0;


                while(currentNumberOfIndividuals < Parameters.populationSize){

                    //initialization
                    if(currentNumberOfIndividuals == 0){
                        newPop.individuals[currentNumberOfIndividuals] = best.myClone();
                        currentNumberOfIndividuals++;

                        newPop.individuals[currentNumberOfIndividuals] = Arrays.stream(pop.individuals).sorted(Comparator.comparingDouble(Solution::calculateFitness).reversed()).limit(2).skip(1).findFirst().get();
                        currentNumberOfIndividuals++;
                        newPop.individuals[currentNumberOfIndividuals] = Arrays.stream(pop.individuals).sorted(Comparator.comparingDouble(Solution::calculateFitness).reversed()).limit(3).skip(2).findFirst().get();
                        currentNumberOfIndividuals++;

                        if(Parameters.populationSize % 2 == 0){
                            Solution s = pop.selection(selection);
                            newPop.individuals[currentNumberOfIndividuals] = s.myClone().mutation();
                            currentNumberOfIndividuals++;
                        }





                    }//ends initialization

                    else {

                        Solution dad = pop.selection(selection);
                        Solution mom = pop.selection(selection);
                        while(dad.getColors().equals(mom.getColors()))
                        {
                            mom = pop.selection(selection);
                        }
                        Solution child1 = new Solution(dad.getWidth(), dad.getHeight(), new ArrayList<Color>());
                        Solution child2 = new Solution(dad.getWidth(), dad.getHeight(), new ArrayList<Color>());

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
                    System.out.println("Changing Image");
                    Thread.sleep(2500);
                    newImage(best);

                }

                System.out.print(gen+1 +".   " + best.calculateFitness());
                System.out.println();
                //gen++;
        }

        }catch( Exception e){
            e.printStackTrace();
        }



    }  // terminates the main method

    private void newImage(Solution best) {
        BufferedImage bufferedImage = new BufferedImage((int) best.getWidth(), (int) best.getHeight(), BufferedImage.TYPE_INT_RGB);
        int[] arr = best.getColors().stream().mapToInt(i -> i.getRGB()).toArray();
        bufferedImage.setRGB(0,0,(int) best.getWidth(),(int) best.getHeight(), arr, 0,(int) best.getWidth());
        MainController.context.drawImage(SwingFXUtils.toFXImage(bufferedImage,null), 0, 0);
    }
    private Solution[] Elitism (Population population, int numberOfSolutionsKept){
        ArrayList<Solution> elite = new ArrayList<>();
        for(var i = 2; i <numberOfSolutionsKept; i++){
            elite.add(population.GetNthSolution(i));
        }
        return elite.toArray(new Solution[0]);
    }


    //crossover
    // problem: if the cutout point is zero the children will be the same as the parents
    private static void crossover(Solution father, Solution mother, Solution child1, Solution child2){
        do{
            child1.setColors(new ArrayList<>());
            child2.setColors(new ArrayList<>());
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
        while(child1.getColors().equals(father.getColors()) ||
                child1.getColors().equals(mother.getColors()) ||
                child2.getColors().equals(father) ||
                child2.getColors().equals(mother));

    }
    private ISelection pickSelectionAlgorithm(SelectionEnum selectionAlg){
        switch (selectionAlg){
            case RouletteWheel ->{
                return new RouletteWheel();
            }
            case Tournament ->{
                return new Tournament(Parameters.tournamentSize);
            }
        }
        return new RouletteWheel();
    }

}
