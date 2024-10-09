package de.fhdortmund.ese.lib.simulation.model.device;

/**
 * IntervalConsumptionDevice is a device that runs on a fixed cycle of idle and active intervals.
 */
public class IntervalConsumptionDevice extends AbstractEnergyDevice {
    private int intervalInSeconds; // The idle duration in seconds before becoming active
    private int activeDurationInSeconds; // The duration in seconds the device remains active
    private int currentIntervalTime = 0; 

    /**
     * Constructs an IntervalConsumptionDevice with a given name, power rating, idle interval, and active duration.
     *
     * @param name                  the name of the device
     * @param powerRating           the power rating of the device in kW
     * @param intervalInSeconds     the number of seconds the device should remain idle before turning active
     * @param activeDurationInSeconds the number of seconds the device should remain active once activated
     */
    public IntervalConsumptionDevice(String name, double powerRating, int intervalInSeconds, int activeDurationInSeconds) {
        super(name, powerRating);
        this.intervalInSeconds = intervalInSeconds;
        this.activeDurationInSeconds = activeDurationInSeconds;
    }


    @Override
    protected void evaluateStatus(int currentTick) {
        if (state == DeviceState.OFF) {
            return;
        }

        currentIntervalTime++;

        if (state == DeviceState.ON_IDLE && currentIntervalTime >= intervalInSeconds) {
            state = DeviceState.ON_ACTIVE;
            currentIntervalTime = 0; // Reset interval timer
            logger.infof("Device: %s, Status: TURNED ACTIVE", name);
        }
        else if (state == DeviceState.ON_ACTIVE && currentIntervalTime >= activeDurationInSeconds) {
            state = DeviceState.ON_IDLE;
            currentIntervalTime = 0; // Reset interval timer
            logger.infof("Device: %s, Status: TURNED IDLE", name);
        }
    }
}
