package de.fhdortmund.ese.web.rest;

import de.fhdortmund.ese.lib.simulation.EnergyManager;
import de.fhdortmund.ese.lib.simulation.SimulationEventLoop;
import de.fhdortmund.ese.web.rest.models.SimulationState;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/simulation")
public class SimulationResource {

    @Inject
    private SimulationEventLoop eventLoop;

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public SimulationState getStatus() {
        return new SimulationState(eventLoop.isRunning(), EnergyManager.getInstance().getTotalEnergyAvailable());
    }

    @POST
    @Path("/start")
    public void startSimulation() {
        eventLoop.startSimulation();
    }

    @POST
    @Path("/stop")
    public void stopSimulation() {
        eventLoop.stopSimulation();
    }



}
