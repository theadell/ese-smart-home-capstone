package de.fhdortmund.ese.lib.simulation.core;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
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

    private ScheduledFuture<?> deviceTask;
    private ScheduledFuture<?> sourceTask;


    public SimulationEventLoop(EventBus bus ,List<EnergyDevice> devices, List<EnergySource> sources, Energy initialEnergy) {
        this.devices = devices;
        this.sources = sources;
        this.balancer = new EnergyBalancer(initialEnergy);
        this.eventBus = bus;
        eventBus.subscribe(balancer);

        deviceTask = executorService.scheduleAtFixedRate(() -> {
            if (state == SimulationState.RUNNING) {
                devices.forEach(EnergyDevice::onTick);
            }
        }, 0, 1, TimeUnit.SECONDS);

        sourceTask = executorService.scheduleAtFixedRate(() -> {
            if (state == SimulationState.RUNNING) {
                sources.forEach(EnergySource::onTick);
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    public void runSimulation() {
        if (state == SimulationState.RUNNING) return;

        state = SimulationState.RUNNING;
    }

    public void pauseSimulation() {
        if (state == SimulationState.STOPPED) return;

        state = SimulationState.STOPPED;
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
    public void cleanup() {
        pauseSimulation();
        
        // Shut down the executor service to free resources
        executorService.shutdown();
        try {
            // Wait for existing tasks to terminate
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Force shutdown if not terminated
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow(); // Force shutdown on interruption
        }
    }


}
