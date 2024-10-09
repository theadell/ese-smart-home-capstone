package de.fhdortmund.ese.lib.simulation.model.device;

import de.fhdortmund.ese.lib.simulation.EnergyManager;

/**
 * SawtoothConsumptionDevice is a device that operates on a sawtooth power consumption pattern.
 */

public class SawtoothConsumptionDevice extends AbstractEnergyDevice {
    private int rampUpDurationInSeconds; // Duration for the ramp-up phase
    private int idleDurationInSeconds;   // Duration for the idle phase
    private int currentTickInCycle = 0;  
    private double currentPowerConsumption = 0.0; 

    private boolean rampingUp = true;    

    /**
     * Constructs a SawtoothConsumptionDevice 
     *
     * @param name                the name of the device
     * @param maxPowerRating      the maximum power rating the device can consume (peak value)
     * @param rampUpDuration      the number of seconds to ramp up to the peak value
     * @param idleDuration        the number of seconds the device remains idle after reaching the peak
     */
    public SawtoothConsumptionDevice(String name, double maxPowerRating, int rampUpDuration, int idleDuration) {
        super(name, maxPowerRating);
        this.rampUpDurationInSeconds = rampUpDuration;
        this.idleDurationInSeconds = idleDuration;
    }

    /**
     * Evaluates the current status of the device at each tick.
     * The device follows a sawtooth power consumption pattern of ramping up to a peak,
     * then resetting to zero, followed by an idle period.
     *
     * @param currentTick the current tick of the clock
     */
    @Override
    protected void evaluateStatus(int currentTick) {
        if (state == DeviceState.OFF) {
            // If the device is OFF, do nothing
            return;
        }

        currentTickInCycle++;

        if (rampingUp) {
            // Ramp-up phase: Gradually increase the power consumption until it reaches the peak
            currentPowerConsumption = (powerRating / rampUpDurationInSeconds) * currentTickInCycle;

            if (currentTickInCycle >= rampUpDurationInSeconds) {
                // Reached the peak value; switch to idle
                state = DeviceState.ON_IDLE;
                currentPowerConsumption = 0.0;
                currentTickInCycle = 0;  // Reset cycle counter
                rampingUp = false; // Switch to idle mode
                logger.infof("Device: %s, Status: REACHED PEAK AND RESET TO IDLE", name);
            } else {
                // Device is actively ramping up
                state = DeviceState.ON_ACTIVE;
                logger.infof("Device: %s, Status: RAMPING UP, Current Power Consumption: %s kW", name, currentPowerConsumption);
            }
        } else {
            // Idle phase: Remain at zero power for the configured idle duration
            if (currentTickInCycle >= idleDurationInSeconds) {
                // End of idle period, begin ramping up again
                state = DeviceState.ON_ACTIVE;
                currentTickInCycle = 0;  // Reset cycle counter
                rampingUp = true; // Start ramping up again
                logger.infof("Device: %s, Status: STARTING RAMP-UP", name);
            } else {
                logger.infof("Device: %s, Status: IDLE", name);
            }
        }
    }

    @Override
    protected void consumeEnergy() {
        double energyConsumed = currentPowerConsumption / 3600; 
        EnergyManager.getInstance().consumeEnergy(name, energyConsumed);
        logger.infof("Device: %s, Rating: %s kW, Consumed: %s kWh", name, currentPowerConsumption, energyConsumed);
    }
}
