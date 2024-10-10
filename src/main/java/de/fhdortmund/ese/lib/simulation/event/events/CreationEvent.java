package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.common.EnergyUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public abstract class CreationEvent implements EnergyEvent {
    private final String entityName;
    private final Power powerRating;

    protected CreationEvent(String entityName, Power powerRating) {
        this.entityName = entityName;
        this.powerRating = powerRating;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    @Override
    public Energy getEnergyAmount() {
        return new Energy(0, EnergyUnit.KILOWATT_HOUR); 
    }

    public Power getPowerRating() {
        return powerRating;
    }
}
