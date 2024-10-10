package de.fhdortmund.ese.lib.simulation.strategy;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class ConstantProductionStrategy implements ProductionStrategy {

    @Override
    public Energy produce(Power power) {
        return power.toEnergy(1);
    }

}