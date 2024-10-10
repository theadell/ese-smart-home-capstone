package de.fhdortmund.ese.lib.simulation.strategy;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public interface ConsumptionStrategy {
    Energy consume(Power power);
}

