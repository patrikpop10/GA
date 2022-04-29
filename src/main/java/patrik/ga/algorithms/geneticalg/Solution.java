package patrik.ga.algorithms.geneticalg;


import patrik.ga.Parameters;
import patrik.ga.algorithms.interfaces.ISolution;
import patrik.ga.util.image.Colors;
import patrik.ga.util.image.Image;
import patrik.ga.util.image.ImageUtils;
import patrik.ga.util.image.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
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

		Solution s = new Solution(super.getWidth(), super.getHeight());
		s.alreadyCalculated = this.alreadyCalculated;
		s.fitness = this.fitness;
		s.setImg(this.getImg());
		s.setColors(this.getColors());
		s.setShapes(this.getShapes());
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
		for(int i = 0; i < super.getShapes().size();i++) {
			if (Math.random() < Parameters.mutationRate) {
				s.getShapes().set(i, new Shape(new Random().nextInt(0, Parameters.imageSize),
						new Random().nextInt(0, Parameters.imageSize),
						new Random().nextInt(0, Parameters.imageSize),
						new Random().nextInt(0, Parameters.imageSize),
						new Random().nextInt(0, Parameters.imageSize),
						new Random().nextInt(0, Parameters.imageSize),
						new Color((int) (Math.random() * 0x1000000))));

			}
			else {
				s.getShapes().set(i,this.getShapes().get(i));

			}

		}// ends the for
		s.draw();
		s.setColors(ImageUtils.getColorsList(s.getImg(), Parameters.imageSize, Parameters.imageSize));
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
			ArrayList<Double> values = new ArrayList<>();
			try{
				IntStream.range(0, this.getColors().size())
						.parallel()
						.forEach(i -> {
							values.add(calcPixelDistance(i));
						});
				res = values.stream().mapToDouble(i -> i.doubleValue()).sum();
			}
			catch (Exception e){
				e.printStackTrace();
			}

			/*
				for (int i = 0; i< this.getColors().size(); i++) {
				values.add(calcPixelDistance(i));
			}
			 */



			alreadyCalculated = true;
			fitness = res;
		}

		return fitness;

	} // terminates calculateFitness method


	private double calcSpecificColorDif(Image baseImage, Colors color, int index){
		switch (color){
			case Red -> {
				return Math.abs(this.getColors().get(index).getRed() - baseImage.getColors().get(index).getRed())/255.0;
			}
			case Green ->{
				return  Math.abs(this.getColors().get(index).getGreen() - baseImage.getColors().get(index).getGreen())/255.0;
			}
			case Blue ->{
				return Math.abs(this.getColors().get(index).getBlue() - baseImage.getColors().get(index).getBlue())/255.0;
			}

		}
		return 0;
	}
	private double calcPixelDistance(int index){
		double redDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Red, index);
		double greenDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Green, index);
		double blueDif = calcSpecificColorDif(Parameters.BaseImage, Colors.Blue, index);
		return redDif + greenDif + blueDif;
	}


} // terminates class Solution
