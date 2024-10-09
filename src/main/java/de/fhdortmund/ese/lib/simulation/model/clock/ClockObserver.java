package de.fhdortmund.ese.lib.simulation.model.clock;

public interface ClockObserver {
    void onTick(int tick);
}