package de.fhdortmund.ese.lib.simulation.strategy;

import de.fhdortmund.ese.lib.simulation.entity.common.EnergyUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class IntervalConsumptionStrategy implements ConsumptionStrategy {
    private final int intervalInSeconds; // Idle duration before activation
    private final int activeDurationInSeconds; // Duration to remain active
    private int currentIntervalTime = 0; // Tracks time in the current state
    private boolean isActive = false; // Tracks whether device is in active phase

    public IntervalConsumptionStrategy(int intervalInSeconds, int activeDurationInSeconds) {
        this.intervalInSeconds = intervalInSeconds;
        this.activeDurationInSeconds = activeDurationInSeconds;
    }

    @Override
    public Energy consume(Power power) {
        if (isActive) {
            // If in active mode, consume energy based on power rating and tick duration
            currentIntervalTime++;
            if (currentIntervalTime >= activeDurationInSeconds) {
                isActive = false;
                currentIntervalTime = 0;
            }
            return power.toEnergy(1); // Active state, return energy consumed per second
        } else {
            // If in idle mode, reset interval and remain idle
            currentIntervalTime++;
            if (currentIntervalTime >= intervalInSeconds) {
                isActive = true;
                currentIntervalTime = 0;
            }
            return new Energy(0, EnergyUnit.KILOWATT_HOUR); // Idle state, no energy consumed
        }
    }
}
