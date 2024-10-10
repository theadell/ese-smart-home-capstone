package de.fhdortmund.ese.lib.simulation.core;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.fhdortmund.ese.lib.simulation.entity.EnergyDevice;
import de.fhdortmund.ese.lib.simulation.entity.EnergySource;
import de.fhdortmund.ese.lib.simulation.entity.common.Energy;
import de.fhdortmund.ese.lib.simulation.event.listeners.EventBus;

public class SimulationEventLoop {
    private final List<EnergyDevice> devices;
    private final List<EnergySource> sources;
    private final EnergyBalancer balancer;

    private final EventBus eventBus;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
    private SimulationState state = SimulationState.STOPPED;

    public SimulationEventLoop(EventBus bus ,List<EnergyDevice> devices, List<EnergySource> sources, Energy initialEnergy) {
        this.devices = devices;
        this.sources = sources;
        this.balancer = new EnergyBalancer(initialEnergy);
        this.eventBus = bus;
        eventBus.subscribe(balancer);
    }

    public void startSimulation() {
        if (state == SimulationState.RUNNING) return;

        state = SimulationState.RUNNING;

        // Schedule devices to tick every second
        executorService.scheduleAtFixedRate(() -> {
            if (state == SimulationState.RUNNING) {
                devices.forEach(EnergyDevice::onTick);
            }
        }, 0, 1, TimeUnit.SECONDS);

        // Schedule sources to tick every second
        executorService.scheduleAtFixedRate(() -> {
            if (state == SimulationState.RUNNING) {
                sources.forEach(EnergySource::onTick);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stopSimulation() {
        state = SimulationState.STOPPED;
        executorService.shutdownNow();
    }

    public boolean isRunning() {
        return state == SimulationState.RUNNING;
    }

    public List<EnergyDevice> getDevices() {
        return devices;
    }

    public List<EnergySource> getSources() {
        return sources;
    }

    public double getCurrentEnergyLevel() {
        return balancer.getTotalEnergy().toKilowattHours();
    }

}
