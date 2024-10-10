package de.fhdortmund.ese.lib.simulation.event.listeners;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyEvent;

import java.util.List;
import java.util.ArrayList;

public class EventBus {
    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void publish(EnergyEvent event) {
        for (EventListener listener : listeners) {
            listener.handle(event);
        }
    }
}
