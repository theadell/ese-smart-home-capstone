package de.fhdortmund.ese.web.rest;

import de.fhdortmund.ese.lib.simulation.EnergyManager;
import de.fhdortmund.ese.lib.simulation.SimulationEventLoop;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/simulation")
public class SimulationResource {

    @Inject
    private SimulationEventLoop eventLoop;

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_HTML)
    public String getStatus() {
        return eventLoop.isRunning() ? 
            "<div id='status'>Simulation is running</div>" :
            "<div id='status'>Simulation is stopped</div>";
    }

    @POST
    @Path("/start")
    @Produces(MediaType.TEXT_HTML)
    public String startSimulation() {
        eventLoop.startSimulation();
        return "<div id='status'>Simulation started</div>";
    }

    @POST
    @Path("/stop")
    @Produces(MediaType.TEXT_HTML)
    public String stopSimulation() {
        eventLoop.stopSimulation();
        return "<div id='status'>Simulation stopped</div>";
    }

    @GET
    @Path("/devices")
    @Produces(MediaType.TEXT_HTML)
    public String getDevices() {
        StringBuilder devicesHtml = new StringBuilder("<ul>");
        for (var device : eventLoop.getDevices()) {
            devicesHtml.append("<li>").append(device.getName()).append("</li>");
        }
        devicesHtml.append("</ul>");
        return devicesHtml.toString();
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.TEXT_HTML)
    public String getSources() {
        StringBuilder sourcesHtml = new StringBuilder("<ul>");
        for (var source : eventLoop.getEnergySources()) {
            sourcesHtml.append("<li>").append(source.getName()).append("</li>");
        }
        sourcesHtml.append("</ul>");
        return sourcesHtml.toString();
    }

    @GET
    @Path("/energy")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEnergyLevel() {
        double energy = EnergyManager.getInstance().getTotalEnergyAvailable();
        return String.format("%.2f", energy);
    }
}
