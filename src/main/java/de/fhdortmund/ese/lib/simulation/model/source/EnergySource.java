package de.fhdortmund.ese.lib.simulation.model.source;

import de.fhdortmund.ese.lib.simulation.model.clock.ClockObserver;

/**
 *  A generic energy source that can generate energy.
 */
public interface EnergySource extends ClockObserver {
    /**
     * Produces energy and adds it to the system.
     */
    void produceEnergy();
}


