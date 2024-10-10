package de.fhdortmund.ese.lib.simulation.strategy;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.PowerUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class SawtoothConsumptionStrategy implements ConsumptionStrategy {
    private final double maxPowerRating;
    private final int rampUpDurationInSeconds;
    private final int idleDurationInSeconds;
    private int currentTickInCycle = 0;
    private boolean rampingUp = true;

    public SawtoothConsumptionStrategy(double maxPowerRating, int rampUpDuration, int idleDuration) {
        this.maxPowerRating = maxPowerRating;
        this.rampUpDurationInSeconds = rampUpDuration;
        this.idleDurationInSeconds = idleDuration;
    }

    @Override
    public Energy consume(Power power) {
        double currentPowerConsumption;

        if (rampingUp) {
            // Calculate power consumption during the ramp-up phase
            currentPowerConsumption = (maxPowerRating / rampUpDurationInSeconds) * currentTickInCycle;

            // Check if ramp-up duration has been reached
            if (++currentTickInCycle >= rampUpDurationInSeconds) {
                // Switch to idle phase
                currentTickInCycle = 0;
                rampingUp = false;
                currentPowerConsumption = maxPowerRating; // Cap at peak
            }
        } else {
            // Idle phase, no power consumed
            currentPowerConsumption = 0.0;

            // Check if idle duration has been reached
            if (++currentTickInCycle >= idleDurationInSeconds) {
                // Restart ramp-up phase
                currentTickInCycle = 0;
                rampingUp = true;
            }
        }

        // Convert current power consumption to energy for the tick duration (1 second)
        Power currentPower = new Power(currentPowerConsumption, PowerUnit.KILOWATT);
        return currentPower.toEnergy(1);
    }
}

