package de.fhdortmund.ese.web.rest;

import java.util.List;

import de.fhdortmund.ese.lib.simulation.EnergyManager;
import de.fhdortmund.ese.lib.simulation.SimulationEventLoop;
import de.fhdortmund.ese.lib.simulation.model.device.AbstractEnergyDevice;
import de.fhdortmund.ese.lib.simulation.model.source.AbstractEnergySource;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
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

    @Inject
    @Location("pub/devicelist.html")
    Template deviceListTemplate;

    @Inject
    @Location("pub/sourcelist.html")
    Template sourceListTemplate;

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_HTML)
    public String getStatus() {
        return eventLoop.isRunning() ? "<div id='status'>Simulation is running</div>"
                : "<div id='status'>Simulation is stopped</div>";
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
    public TemplateInstance getDevices() {
        List<AbstractEnergyDevice> devices = eventLoop.getDevices();

        return deviceListTemplate.data("devices", devices);
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSources() {
        List<AbstractEnergySource> sources = eventLoop.getEnergySources();

        return sourceListTemplate.data("sources", sources);
    }

    @GET
    @Path("/energy")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEnergyLevel() {
        double energy = EnergyManager.getInstance().getTotalEnergyAvailable();
        return String.format("%.2f", energy);
    }

}
