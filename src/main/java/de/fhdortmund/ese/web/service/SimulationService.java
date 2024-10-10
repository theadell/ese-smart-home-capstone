package de.fhdortmund.ese.web.service;

import java.util.List;
import java.util.ArrayList;

import de.fhdortmund.ese.lib.simulation.core.SimulationEventLoop;
import de.fhdortmund.ese.lib.simulation.entity.EnergyDevice;
import de.fhdortmund.ese.lib.simulation.entity.EnergySource;
import de.fhdortmund.ese.lib.simulation.entity.device.ConstantConsumptionDevice;
import de.fhdortmund.ese.lib.simulation.entity.device.IntervalConsumptionDevice;
import de.fhdortmund.ese.lib.simulation.entity.device.SawtoothConsumptionDevice;
import de.fhdortmund.ese.lib.simulation.entity.source.SolarPanel;
import de.fhdortmund.ese.lib.simulation.entity.source.WindTurbine;
import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;
import de.fhdortmund.ese.lib.simulation.entity.common.EnergyUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Power;
import de.fhdortmund.ese.lib.simulation.entity.common.PowerUnit;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;

@Singleton
public class SimulationService {
    private SimulationEventLoop simulationEventLoop;
    private final EventBus eventBus = new EventBus();

    @PostConstruct
    public void initialize() {


        List<EnergyDevice> devices = new ArrayList<>();

        devices.add(new ConstantConsumptionDevice("Refrigerator-001", new Power(3, PowerUnit.KILOWATT), eventBus));
        devices.add(new ConstantConsumptionDevice("Freezer-002", new Power(3, PowerUnit.KILOWATT), eventBus));
        devices.add(new ConstantConsumptionDevice("AirConditioner-003", new Power(4, PowerUnit.KILOWATT), eventBus));
        devices.add(new IntervalConsumptionDevice("Heater-004", new Power(2.0, PowerUnit.KILOWATT), 10, 5, eventBus));
        devices.add(new IntervalConsumptionDevice("WaterHeater-005", new Power(2.5, PowerUnit.KILOWATT), 8, 3, eventBus));
        devices.add(new SawtoothConsumptionDevice("WaterPump-006", new Power(4.0, PowerUnit.KILOWATT), 4.0, 6, 4, eventBus));
        devices.add(new SawtoothConsumptionDevice("HydroBooster-007", new Power(3.5, PowerUnit.KILOWATT), 3.5, 5, 2, eventBus));


        List<EnergySource> sources = new ArrayList<>();
        sources.add(new SolarPanel("SolarPanel-001", new Power(0.1, PowerUnit.KILOWATT), eventBus));
        sources.add(new WindTurbine("WindTurbine-002", new Power(0.15, PowerUnit.KILOWATT), eventBus));

        Energy initialEnergy = new Energy(100, EnergyUnit.KILOWATT_HOUR);

        simulationEventLoop = new SimulationEventLoop(eventBus, devices, sources, initialEnergy);
    }

    public void startSimulation() {
        simulationEventLoop.runSimulation();
    }

    public void stopSimulation() {
        simulationEventLoop.pauseSimulation();
    }

    public boolean isRunning() {
        return simulationEventLoop.isRunning();
    }

    public List<EnergyDevice> getDevices() {
        return simulationEventLoop.getDevices();
    }

    public List<EnergySource> getSources() {
        return simulationEventLoop.getSources();
    }

    public double getCurrentEnergyLevel() {
        return simulationEventLoop.getCurrentEnergyLevel();
    }

    @PreDestroy
    public void cleanup() {
        simulationEventLoop.cleanup();
    }
}
