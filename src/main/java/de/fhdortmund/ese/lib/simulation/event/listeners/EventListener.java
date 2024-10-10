package de.fhdortmund.ese.lib.simulation.event.listeners;

import de.fhdortmund.ese.lib.simulation.event.events.EnergyEvent;

public interface EventListener {
    void handle(EnergyEvent event);
}

