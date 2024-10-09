package de.fhdortmund.ese.lib.simulation.model.device;

import org.slf4j.Logger;

public abstract class AbstractEnergyDevice {
    protected String name;
    protected double powerRating; // Power in kW
    protected DeviceState state;

    protected Logger logger;
    
    public DeviceState getState() {
        return state;
    }

    public AbstractEnergyDevice(String name, double powerRating) {
        this.name = name;
        this.powerRating = powerRating;
        this.state = DeviceState.ON_ACTIVE; 
    }

    // Method to change the state of the device
    public void setState(DeviceState newState) {
        this.state = newState;
        logger.info("Device: {}, New State: {}", name, newState);
    }



    // Determine if device should consume energy at currentTick
    protected abstract void evaluateStatus(int currentTick);

    protected void consumeEnergy() {
        double energyConsumed = powerRating / 3600; 
        logger.info("Device: {}, Rating: {} kW, Consumed: {} kWh", name, powerRating, energyConsumed);
    }


    public String getName() {
        return name;
    }

    public double getPowerRating() {
        return powerRating;
    }

    public Logger getLogger() {
        return logger;
    }
}

