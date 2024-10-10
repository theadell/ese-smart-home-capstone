package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class EnergyConsumptionEvent implements EnergyEvent {
    private final String entityName;
    private final Energy energyAmount;

    public EnergyConsumptionEvent(String entityName, Energy energyAmount) {
        this.entityName = entityName;
        this.energyAmount = energyAmount;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public Energy getEnergyAmount() {
        return energyAmount;
    }
}

