package de.fhdortmund.ese.lib.simulation.model.source;

/**
 * Represents a wind turbine energy source.
 * Produces energy at a fixed rate per tick.
 */
public class WindTurbine extends AbstractEnergySource {
    public WindTurbine(String name, double energyPerTick) {
        super(name, energyPerTick);
    }

    @Override
    public void produceEnergy() {
        super.produceEnergy();
    }
}
