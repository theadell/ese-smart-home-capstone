package de.fhdortmund.ese.lib.simulation.entity.common;

public class Power {
    private final double value;
    private final PowerUnit unit;

    public Power(double value, PowerUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public PowerUnit getUnit() {
        return unit;
    }

    // Converts power to kilowatts
    public double toKilowatts() {
        return unit == PowerUnit.WATT ? value / 1000.0 : value;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    public Energy toEnergy(int durationInSeconds) {
        double powerInKilowatts = this.toKilowatts();
        double energyInKWh = powerInKilowatts * (durationInSeconds / 3600.0);
        return new Energy(energyInKWh, EnergyUnit.KILOWATT_HOUR);
    }

}