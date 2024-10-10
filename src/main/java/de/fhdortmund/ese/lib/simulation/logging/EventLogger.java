package de.fhdortmund.ese.lib.simulation.logging;

import org.jboss.logging.Logger;

import de.fhdortmund.ese.lib.simulation.event.events.DeviceCreationEvent;
import de.fhdortmund.ese.lib.simulation.event.events.DeviceStateChangeEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyConsumptionEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyProductionEvent;
import de.fhdortmund.ese.lib.simulation.event.events.SourceCreationEvent;


public class EventLogger {
    private static final Logger deviceLogger = Logger.getLogger("DeviceLogger");
    private static final Logger sourceLogger = Logger.getLogger("EnergySourceLogger");

    public static void logEvent(EnergyEvent event) {
        switch (event) {
            case DeviceCreationEvent deviceEvent -> logDeviceCreation(deviceEvent);
            case SourceCreationEvent sourceEvent -> logSourceCreation(sourceEvent);
            case DeviceStateChangeEvent stateChangeEvent -> logDeviceStateChange(stateChangeEvent);
            case EnergyConsumptionEvent consumptionEvent -> logEnergyConsumption(consumptionEvent);
            case EnergyProductionEvent productionEvent -> logEnergyProduction(productionEvent);
            default -> throw new IllegalArgumentException("Unhandled event type: " + event.getClass().getName());
        }
    }
    

    private static void logDeviceCreation(DeviceCreationEvent event) {
        deviceLogger.infof("Device Created: %s, Power Rating: %.2f kW",
                event.getEntityName(), event.getPowerRating().toKilowatts());
    }

    private static void logSourceCreation(SourceCreationEvent event) {
        sourceLogger.infof("Energy Source Created: %s, Power Rating: %.2f kW",
                event.getEntityName(), event.getPowerRating().toKilowatts());
    }

    private static void logDeviceStateChange(DeviceStateChangeEvent event) {
        deviceLogger.infof("Device: %s, New State: %s", event.getEntityName(), event.getNewState());
    }

    private static void logEnergyConsumption(EnergyConsumptionEvent event) {
        deviceLogger.infof("Device: %s, Rating: %.2f kW, Consumed: %.3f kWh",
                event.getEntityName(), event.getEnergyAmount().toKilowattHours(), event.getEnergyAmount().toKilowattHours());
    }

    private static void logEnergyProduction(EnergyProductionEvent event) {
        sourceLogger.infof("Energy Source: %s, Produced: %.3f kWh",
                event.getEntityName(), event.getEnergyAmount().toKilowattHours());
    }
}
