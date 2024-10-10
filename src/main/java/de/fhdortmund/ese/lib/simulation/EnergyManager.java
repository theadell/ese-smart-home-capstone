package de.fhdortmund.ese.lib.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * EnergyManager controls energy flow from and to the system
 */
public class EnergyManager {
    private static EnergyManager instance;
    private double totalEnergyAvailable;
    private final Logger logger;

    private EnergyManager(double initialEnergy) {
        this.totalEnergyAvailable = initialEnergy;
        this.logger = LoggerFactory.getLogger(EnergyManager.class);
    }

    public static synchronized EnergyManager getInstance() {
        if (instance == null) {
            instance = new EnergyManager(100.0); // Initialize with 100 kWh
        }
        return instance;
    }

    /**
     * Consumes energy from the system by a device.
     *
     * @param deviceName  the name of the consuming device
     * @param energyNeeded the amount of energy needed in kWh
     */
    public synchronized void consumeEnergy(String deviceName, double energyNeeded) {
        if (totalEnergyAvailable >= energyNeeded) {
            totalEnergyAvailable -= energyNeeded;
            logger.info("EnergyManager: Device: {}, Consumed: {} kWh, Remaining: {} kWh",
                    deviceName, energyNeeded, totalEnergyAvailable);
        } else {
            logger.warn("EnergyManager: Insufficient energy for Device: {}, Needed: {}, Available: {}",
                    deviceName, energyNeeded, totalEnergyAvailable);
        }
    }

    /**
     * Adds energy to the system from an energy source.
     *
     * @param sourceName  the name of the energy source
     * @param energyProduced the amount of energy produced in kWh
     */
    public synchronized void addEnergy(String sourceName, double energyProduced) {
        totalEnergyAvailable += energyProduced;
        logger.info("EnergyManager: Source: {}, Added: {} kWh, Total Available: {} kWh",
                sourceName, energyProduced, totalEnergyAvailable);
    }


    public double getTotalEnergyAvailable() {
        return totalEnergyAvailable;
    }
}
