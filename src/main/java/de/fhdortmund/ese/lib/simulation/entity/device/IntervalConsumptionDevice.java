package de.fhdortmund.ese.lib.simulation.entity.device;

import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.IntervalConsumptionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class IntervalConsumptionDevice extends AbstractEnergyDevice {
    public IntervalConsumptionDevice(String name, Power power, int idleInterval, int activeDuration, EventBus eventBus) {
        super(name, power, new IntervalConsumptionStrategy(idleInterval, activeDuration), eventBus);
    }
}
