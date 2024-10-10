package de.fhdortmund.ese.lib.simulation.core;

import org.jboss.logging.Logger;

import de.fhdortmund.ese.lib.simulation.event.events.EnergyConsumptionEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyEvent;
import de.fhdortmund.ese.lib.simulation.event.events.EnergyProductionEvent;
import de.fhdortmund.ese.lib.simulation.event.listeners.EventListener;
import de.fhdortmund.ese.lib.simulation.entity.common.EnergyUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;
import de.fhdortmund.ese.lib.simulation.logging.EventLogger;

public class EnergyBalancer implements EventListener {
    private static final Logger logger = Logger.getLogger(EnergyBalancer.class);

    private Energy totalEnergy;

    public EnergyBalancer(Energy initialEnergy) {
        this.totalEnergy = initialEnergy;
    }

    @Override
    public synchronized void handle(EnergyEvent event) {
        EventLogger.logEvent(event);
        Energy eventEnergy = event.getEnergyAmount();

        switch (event) {
            case EnergyConsumptionEvent consumptionEvent -> {
                double newEnergyValue = totalEnergy.toKilowattHours() - eventEnergy.toKilowattHours();
                if (newEnergyValue >= 0) {
                    totalEnergy = new Energy(newEnergyValue, EnergyUnit.KILOWATT_HOUR);
                    logger.infof("Consumed %.3f kWh by %s, Remaining Energy: %.3f kWh",
                            eventEnergy.toKilowattHours(), event.getEntityName(), totalEnergy.toKilowattHours());
                } else {
                    logger.warnf("Failed to consume %.3f kWh by %s - Insufficient Energy. Remaining Energy: %.3f kWh",
                            eventEnergy.toKilowattHours(), event.getEntityName(), totalEnergy.toKilowattHours());
                }
            }
            case EnergyProductionEvent productionEvent -> {
                double newEnergyValue = totalEnergy.toKilowattHours() + eventEnergy.toKilowattHours();
                totalEnergy = new Energy(newEnergyValue, EnergyUnit.KILOWATT_HOUR);
                logger.infof("Produced %.3f kWh by %s, Total Available Energy: %.3f kWh",
                        eventEnergy.toKilowattHours(), event.getEntityName(), totalEnergy.toKilowattHours());
            }
            default -> {
                logger.errorf("Unhandled event type: %s", event.getClass().getName());
                throw new IllegalArgumentException("Unhandled event type: " + event.getClass().getName());
            }
        }
    }

    public synchronized Energy getTotalEnergy() {
        return totalEnergy;
    }
}