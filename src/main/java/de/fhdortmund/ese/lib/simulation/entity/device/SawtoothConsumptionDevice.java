package de.fhdortmund.ese.lib.simulation.entity.device;

import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.SawtoothConsumptionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class SawtoothConsumptionDevice extends AbstractEnergyDevice {
    public SawtoothConsumptionDevice(String name, Power power, double maxPower, int rampUpDuration, int idleDuration, EventBus eventBus) {
        super(name, power, new SawtoothConsumptionStrategy(maxPower, rampUpDuration, idleDuration), eventBus);
    }
}
