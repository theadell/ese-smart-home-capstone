package de.fhdortmund.ese.lib.simulation.entity;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public interface EnergyEntity {
    String getName();
    Power getPower();
    void onTick();
}
