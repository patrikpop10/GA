package patrik.ga.util;

import javafx.concurrent.Task;
import patrik.ga.algorithms.geneticalg.GA;

public class GAService extends javafx.concurrent.Service<GA> {
    private GA ga;
    public GAService(GA ga){
        this.ga = ga;
    }

    @Override
    protected Task<GA> createTask() {
        return new Task<GA>() {
            @Override
            protected GA call() throws Exception {
                ga.run();
                return null;
            }
        };
    }
}
