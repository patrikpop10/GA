package patrik.ga.algorithms.interfaces;

import patrik.ga.algorithms.geneticalg.Solution;

public interface ISelection {
    Solution select(Solution[] individuals);
}
