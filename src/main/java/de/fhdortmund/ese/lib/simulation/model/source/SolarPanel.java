package de.fhdortmund.ese.lib.simulation.model.source;

/**
 * Represents a solar panel energy source.
 * Produces energy at a fixed rate per tick.
 */
public class SolarPanel extends AbstractEnergySource {
    public SolarPanel(String name, double energyPerTick) {
        super(name, energyPerTick);
    }

    @Override
    public void produceEnergy() {
        super.produceEnergy();
    }
}
