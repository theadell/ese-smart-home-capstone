package de.fhdortmund.ese.lib.simulation.entity.source;

import de.fhdortmund.ese.lib.simulation.entity.EnergySource;
import de.fhdortmund.ese.lib.simulation.event.events.DeviceCreationEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyProductionEvent;
import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.ProductionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public abstract class AbstractEnergySource implements EnergySource {
    protected final String name;
    protected final Power power;
    protected final ProductionStrategy productionStrategy;
    protected final EventBus eventBus;

    public AbstractEnergySource(String name, Power power, ProductionStrategy productionStrategy, EventBus eventBus) {
        this.name = name;
        this.power = power;
        this.productionStrategy = productionStrategy;
        this.eventBus = eventBus;
        eventBus.publish(new DeviceCreationEvent(name, power));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Power getPower() {
        return power;
    }

    @Override
    public void onTick() {
        produceEnergy();
    }

    @Override
    public void produceEnergy() {
        Energy energyProduced = productionStrategy.produce(power);
        eventBus.publish(new EnergyProductionEvent(name, energyProduced));
    }
}
