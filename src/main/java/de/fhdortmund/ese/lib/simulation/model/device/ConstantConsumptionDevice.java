package de.fhdortmund.ese.lib.simulation.model.device;

public class ConstantConsumptionDevice extends AbstractEnergyDevice {

    public ConstantConsumptionDevice(String name, double powerRating) {
        super(name, powerRating);
    }

    @Override
    protected void evaluateStatus(int currentTick) {
        // NOOP -> should always be on ACTIVE
    }

}
