package de.fhdortmund.ese.lib.simulation.strategy;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class ConstantConsumptionStrategy implements ConsumptionStrategy {

    @Override
    public Energy consume(Power power) {
        return power.toEnergy(1);
    }
}
