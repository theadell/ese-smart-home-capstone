package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public interface EnergyEvent {
    String getEntityName();
    Energy getEnergyAmount(); 
}