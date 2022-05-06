package patrik.ga.algorithms.interfaces;

import patrik.ga.algorithms.geneticalg.Solution;

public interface ICrossover {
   void cross(Solution father, Solution mother, Solution son, Solution daughter);
}
