package de.fhdortmund.ese.lib.simulation.model.clock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationClock extends Thread {
    private static SimulationClock instance;
    private final Set<ClockObserver> observers = new HashSet<>();
    private boolean running = false;
    private AtomicInteger currentTick = new AtomicInteger(0);


    private SimulationClock() {}

    public static synchronized SimulationClock getInstance() {
        if (instance == null) {
            instance = new SimulationClock();
        }
        return instance;
    }

    public void addObserver(ClockObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ClockObserver observer) {
        observers.remove(observer);
    }

    public void stopClock() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(1000); 
                int tick = currentTick.incrementAndGet();
                for (ClockObserver observer : observers) {
                    observer.onTick(tick);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getCurrentTick() {
        return currentTick.get();
    }
}

