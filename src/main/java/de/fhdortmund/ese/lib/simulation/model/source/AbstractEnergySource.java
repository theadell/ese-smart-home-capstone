package de.fhdortmund.ese.lib.simulation.model.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhdortmund.ese.lib.simulation.EnergyManager;

public abstract class AbstractEnergySource implements EnergySource, Runnable {
    protected String name;
    protected double energyPerTick; 
    protected Logger logger;

    public AbstractEnergySource(String name, double energyPerTick) {
        this.name = name;
        this.energyPerTick = energyPerTick;
        this.logger = LoggerFactory.getLogger(name);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public void onTick(int currentTick) {
        produceEnergy();
    }

    @Override
    public void produceEnergy() {
        EnergyManager.getInstance().addEnergy(name, energyPerTick);
        logger.info("EnergySource: {}, Produced: {} kWh", name, energyPerTick);
    }


    public String getName() {
        return name;
    }

    public double getEnergyPerTick() {
        return energyPerTick;
    }

    public Logger getLogger() {
        return logger;
    }

}
