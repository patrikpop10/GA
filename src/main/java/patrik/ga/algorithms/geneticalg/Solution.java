package patrik.ga.algorithms.geneticalg;


import patrik.ga.Parameters;
import patrik.ga.algorithms.interfaces.ISolution;
import patrik.ga.util.image.Colors;
import patrik.ga.util.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.stream.IntStream;

public class Solution extends Image implements ISolution {

	/*--------------------------------------------------------------------
	 *
	 *
	 *   Constructor
	 *
	 *    It creates the string representing a solution and
	 *    initializes it at random
	 *
	 -----------------------------------------------------------------*/

	Solution(double width, double height, ArrayList<Color> colors) {
		super(width, height, colors);

		alreadyCalculated = false;
		fitness = -1;
	}
	Solution(double width, double height) {
		super(width, height);
		for(int i = 0; i < Parameters.BaseImage.getColors().size(); i++){
			this.getColors().add(i, new Color((int)(Math.random() * 0x1000000)));
		}

		alreadyCalculated = false;
		fitness = -1;
	}


	/*----------------------------------------------------------------------
	 *
	 *
	 *   Attributes
	 *
	 *    The principal attribute of a Solution is its representation.
	 *    In this specific case, a boolean vector (which will
	 *    represent a binary string.
	 *
	 */

	private boolean alreadyCalculated;
	private double fitness;



	/*
	 *   Methods
	 */


	/*
	 *
	 * myClone
	 *
	 * creates an identical copy of the current solution
	 *
	 */


	public Solution myClone() {

		Solution s = new Solution(super.getWidth(), super.getHeight(), super.getColors());
		s.alreadyCalculated = this.alreadyCalculated;
		s.fitness = this.fitness;
		return s;

	} //ends method myClone


	/*
	 *
	 * mutation
	 *
	 * implements the standard mutation of GAs
	 *
	 */


	public Solution mutation() {

		Solution s = new Solution(super.getWidth(), super.getHeight());
		for(int i = 0; i < super.getColors().size();i++) {
			if (Math.random() < Parameters.mutationRate)
				s.getColors().set(i, new Color((int) (Math.random() * 0x1000000)));
			else {
				s.getColors().set(i,this.getColors().get(i));
			}
		}// ends the for


		s.alreadyCalculated = false;
		s.fitness = -1;
		return s;

	}// ends method mutation


	/*-------------------------------------------------------------------
	 *
	 *   printSolution
	 *
	 *     It prints the binary string representing a solution,
	 *     and also its fitness value.
	 */
	public String printSolution() {
		StringBuilder stringBuilder = new StringBuilder();
		for (var color : this.getColors()) {
			System.out.print(color + ", ");
		}
		System.out.println ("          " + calculateFitness());
		stringBuilder.append("          " + calculateFitness() +"\n");
		return stringBuilder.toString();

	}   // terminates method printSolution




	/*-------------------------------------------------------------------
	 *
	 *   calculateFitness
	 *
	 *      It calculates the fitness of a solution.
	 *
	 */

	public double calculateFitness() {
		if(!alreadyCalculated) {
			double res= 0.0;
			try{
				var time = System.currentTimeMillis();
				ArrayList<Double> values = new ArrayList<Double>();


			for (int i = 0; i< this.getColors().size(); i++) {
				values.add(calcPixelDistance(i));
			}
				res = values.stream().mapToDouble(i -> i.doubleValue()).sum();

				alreadyCalculated = true;
				fitness = res;
			}
			catch(Exception e ){
				e.printStackTrace();
			}

		}

		return fitness;

	} // terminates calculateFitness method
	private double calcSpecificColorDif(Image baseImage, Colors color, int index){
		switch (color){
			case Red -> {
				return Math.pow(this.getColors().get(index).getRed() - baseImage.getColors().get(index).getRed(), 2);
			}
			case Green ->{
				return  Math.pow(this.getColors().get(index).getGreen() - baseImage.getColors().get(index).getGreen(),2);
			}
			case Blue ->{
				return Math.pow(this.getColors().get(index).getBlue() - baseImage.getColors().get(index).getBlue(),2);
			}

		}
		return 0;
	}
	private double calcPixelDistance(int index){
		double redDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Red, index);
		double greenDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Green, index);
		double blueDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Blue, index);
		return (redDif + greenDif + blueDif) / (3 * Parameters.imageSize * Parameters.imageSize);
	}


} // terminates class Solution
