package de.fhdortmund.ese.lib.simulation.model.device;

import org.slf4j.Logger;

import de.fhdortmund.ese.lib.simulation.EnergyManager;
import de.fhdortmund.ese.lib.simulation.model.clock.ClockObserver;

public abstract class AbstractEnergyDevice implements ClockObserver, Runnable {
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
        EnergyManager.getInstance().consumeEnergy(name, energyConsumed);
        logger.info("Device: {}, Rating: {} kW, Consumed: {} kWh", name, powerRating, energyConsumed);
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
        evaluateStatus(currentTick);
        if (state == DeviceState.ON_ACTIVE) {
            consumeEnergy();
        } else if (state == DeviceState.ON_IDLE) {
            logger.info("Device: {}, Status: Idle", name);
        }
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
