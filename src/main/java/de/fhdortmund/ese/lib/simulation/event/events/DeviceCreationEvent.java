package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class DeviceCreationEvent extends CreationEvent {
    public DeviceCreationEvent(String entityName, Power powerRating) {
        super(entityName, powerRating);
    }
}
