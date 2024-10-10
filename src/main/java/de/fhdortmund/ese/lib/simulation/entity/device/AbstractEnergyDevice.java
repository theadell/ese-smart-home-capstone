package de.fhdortmund.ese.lib.simulation.entity.device;

import de.fhdortmund.ese.lib.simulation.entity.EnergyDevice;
import de.fhdortmund.ese.lib.simulation.event.events.DeviceCreationEvent;
import de.fhdortmund.ese.lib.simulation.event.events.DeviceStateChangeEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyConsumptionEvent;
import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.strategy.ConsumptionStrategy;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public abstract class AbstractEnergyDevice implements EnergyDevice {
    protected final String name;
    protected final Power power;
    protected final ConsumptionStrategy consumptionStrategy;
    protected final EventBus eventBus;
    protected DeviceState state;


    public AbstractEnergyDevice(String name, Power power, ConsumptionStrategy consumptionStrategy, EventBus eventBus) {
        this.name = name;
        this.power = power;
        this.consumptionStrategy = consumptionStrategy;
        this.eventBus = eventBus;
        this.state = DeviceState.ON_ACTIVE;
        eventBus.publish(new DeviceCreationEvent(name, power));
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState newState) {
        if (this.state != newState) {
            this.state = newState;
            eventBus.publish(new DeviceStateChangeEvent(name, newState));
        }
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
        consumeEnergy();
    }

    @Override
    public void consumeEnergy() {
        Energy energyConsumed = consumptionStrategy.consume(power);
        eventBus.publish(new EnergyConsumptionEvent(name, energyConsumed));
    }
}