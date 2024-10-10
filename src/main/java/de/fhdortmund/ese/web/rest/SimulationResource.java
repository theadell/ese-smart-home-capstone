package de.fhdortmund.ese.web.rest;

import java.util.List;

import de.fhdortmund.ese.lib.simulation.entity.EnergyDevice;
import de.fhdortmund.ese.lib.simulation.entity.EnergySource;
import de.fhdortmund.ese.web.service.SimulationService;
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
    private SimulationService simulationService;

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
        return simulationService.isRunning() ? "<div id='status'>Simulation is running</div>"
                : "<div id='status'>Simulation is stopped</div>";
    }

    @POST
    @Path("/start")
    @Produces(MediaType.TEXT_HTML)
    public String startSimulation() {
        simulationService.startSimulation();
        return "<div id='status'>Simulation started</div>";
    }

    @POST
    @Path("/stop")
    @Produces(MediaType.TEXT_HTML)
    public String stopSimulation() {
        simulationService.stopSimulation();
        return "<div id='status'>Simulation stopped</div>";
    }

    @GET
    @Path("/devices")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getDevices() {
        List<EnergyDevice> devices = simulationService.getDevices();

        return deviceListTemplate.data("devices", devices);
    }

    @GET
    @Path("/sources")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getSources() {
        List<EnergySource> sources = simulationService.getSources();

        return sourceListTemplate.data("sources", sources);
    }

    @GET
    @Path("/energy")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEnergyLevel() {
        double energy = simulationService.getCurrentEnergyLevel();
        return String.format("%.2f", energy);
    }

}
