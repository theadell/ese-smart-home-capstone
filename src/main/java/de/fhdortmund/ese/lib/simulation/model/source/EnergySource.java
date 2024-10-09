package de.fhdortmund.ese.lib.simulation.model.source;

/**
 *  A generic energy source that can generate energy.
 */
public interface EnergySource {
    /**
     * Produces energy and adds it to the system.
     */
    void produceEnergy();
}


