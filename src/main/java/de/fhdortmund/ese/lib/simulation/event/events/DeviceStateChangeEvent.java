package de.fhdortmund.ese.lib.simulation.event.events;

import de.fhdortmund.ese.lib.simulation.entity.device.DeviceState;
import de.fhdortmund.ese.lib.simulation.entity.common.EnergyUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;

public class DeviceStateChangeEvent implements EnergyEvent {
    private final String deviceName;
    private final DeviceState newState;

    public DeviceStateChangeEvent(String deviceName, DeviceState newState) {
        this.deviceName = deviceName;
        this.newState = newState;
    }

    @Override
    public String getEntityName() {
        return deviceName;
    }

    @Override
    public Energy getEnergyAmount() {
        return new Energy(0, EnergyUnit.KILOWATT_HOUR); // State change has no energy value
    }

    public DeviceState getNewState() {
        return newState;
    }
}
