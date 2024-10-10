package de.fhdortmund.ese.lib.simulation.entity.source;

import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.ConstantProductionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class WindTurbine extends AbstractEnergySource {
    public WindTurbine(String name, Power power, EventBus eventBus) {
        super(name, power, new ConstantProductionStrategy(), eventBus);
    }
}
