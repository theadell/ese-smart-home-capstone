package de.fhdortmund.ese.lib.simulation;

import java.util.ArrayList;
import java.util.List;

import de.fhdortmund.ese.lib.simulation.model.source.EnergySource;
import de.fhdortmund.ese.lib.simulation.model.source.SolarPanel;
import de.fhdortmund.ese.lib.simulation.model.source.WindTurbine;
import de.fhdortmund.ese.lib.simulation.model.clock.SimulationClock;
import de.fhdortmund.ese.lib.simulation.model.device.AbstractEnergyDevice;
import de.fhdortmund.ese.lib.simulation.model.device.ConstantConsumptionDevice;
import de.fhdortmund.ese.lib.simulation.model.device.IntervalConsumptionDevice;
import de.fhdortmund.ese.lib.simulation.model.device.SawtoothConsumptionDevice;
import jakarta.inject.Singleton;

@Singleton
public class SimulationEventLoop {
    private List<AbstractEnergyDevice> devices;
    private List<EnergySource> energySources;

    private List<Thread> deviceThreads;
    private List<Thread> sourceThreads;
    private SimulationClock clock;
    private boolean isRunning = false;

    public SimulationEventLoop() {

        // init simulation devices and sources 
        
        devices = new ArrayList<>();
        energySources = new ArrayList<>();
        deviceThreads = new ArrayList<>();
        sourceThreads = new ArrayList<>();
        clock = SimulationClock.getInstance();
        devices.add(new ConstantConsumptionDevice("Refrigerator-001", 3));
        devices.add(new ConstantConsumptionDevice("Freezer-002", 3));
        devices.add(new ConstantConsumptionDevice("AirConditdoner-003", 4));

        devices.add(new IntervalConsumptionDevice("Heater-004", 2.0, 10, 5));
        devices.add(new IntervalConsumptionDevice("WaterHeater-005", 2.5, 8, 3));

        devices.add(new SawtoothConsumptionDevice("WaterPump-006", 4.0, 6, 4));
        devices.add(new SawtoothConsumptionDevice("HydroBooster-007", 3.5, 5, 2));

        energySources.add(new SolarPanel("SolarPanel-001", 0.1));
        energySources.add(new WindTurbine("WindTurbine-002", 0.15));
    }

    public synchronized void startSimulation() {
        if (!isRunning) {

            devices.forEach(device -> {
                Thread deviceThread = new Thread(device);
                deviceThreads.add(deviceThread);
                clock.addObserver(device);
                deviceThread.start();
            });

            energySources.forEach(source -> {
                Thread sourceThread = new Thread((Runnable) source);
                sourceThreads.add(sourceThread);
                clock.addObserver(source);
                sourceThread.start();
            });

            Thread clockThread = new Thread(clock);
            clockThread.start();
            isRunning = true;
        }
    }

    public synchronized void stopSimulation() {
        if (isRunning) {
            clock.stopClock();

            deviceThreads.forEach(Thread::interrupt);
            sourceThreads.forEach(Thread::interrupt);

            deviceThreads.clear();
            sourceThreads.clear();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public List<EnergySource> getEnergySources() {
        return energySources;
    }

    public void setEnergySources(List<EnergySource> energySources) {
        this.energySources = energySources;
    }

    public List<AbstractEnergyDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<AbstractEnergyDevice> devices) {
        this.devices = devices;
    }

}
