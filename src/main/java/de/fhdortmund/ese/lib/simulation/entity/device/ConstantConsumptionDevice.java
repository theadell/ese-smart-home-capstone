package de.fhdortmund.ese.lib.simulation.entity.device;

import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.ConstantConsumptionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class ConstantConsumptionDevice extends AbstractEnergyDevice {
    public ConstantConsumptionDevice(String name, Power power, EventBus eventBus) {
        super(name, power, new ConstantConsumptionStrategy(), eventBus);
    }
}
