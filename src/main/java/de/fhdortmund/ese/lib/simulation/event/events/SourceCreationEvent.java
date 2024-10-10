package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.common.Power;

public class SourceCreationEvent extends CreationEvent {
    public SourceCreationEvent(String entityName, Power powerRating) {
        super(entityName, powerRating);
    }
}
