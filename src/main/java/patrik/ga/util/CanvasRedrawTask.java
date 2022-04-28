package patrik.ga.util;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.concurrent.atomic.AtomicReference;

// generic task that redraws the canvas when new data arrives
// (but not more often than 60 times per second).
    public abstract class CanvasRedrawTask<T> extends AnimationTimer {
        private final AtomicReference<T> data = new AtomicReference<T>(null);
        private final Canvas canvas;

        public CanvasRedrawTask(Canvas canvas) {
            this.canvas = canvas;
        }

        public void requestRedraw(T dataToDraw) {
            data.set(dataToDraw);
            start(); // in case, not already started
        }

        public void handle(long now) {
            // check if new data is available
            T dataToDraw = data.getAndSet(null);
            if (dataToDraw != null) {
                redraw(canvas.getGraphicsContext2D(), dataToDraw);
            }
        }

        protected abstract void redraw(GraphicsContext context, T data);
    }


