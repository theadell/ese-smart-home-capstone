package de.fhdortmund.ese.lib.simulation.entity.common;

public class Energy {
    private final double value;
    private final EnergyUnit unit;

    public Energy(double value, EnergyUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public EnergyUnit getUnit() {
        return unit;
    }

    public double toKilowattHours() {
        return unit == EnergyUnit.WATT_HOUR ? value / 1000.0 : value;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}