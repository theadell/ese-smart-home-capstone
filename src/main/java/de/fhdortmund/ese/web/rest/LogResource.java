package de.fhdortmund.ese.web.rest;

import java.util.List;

import de.fhdortmund.ese.web.service.LogService;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/logs")
public class LogResource {

    @Inject
    LogService logService;

    @Inject
    @Location("pub/devices.html") 
    Template devices;

    @Inject
    @Location("pub/logpage.html") 
    Template logsPage;

    @Inject
    @Location("pub/searchresult.html") 
    Template searchResultsTemplate;

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance searchLogs(@QueryParam("query") String query,
                                       @QueryParam("page") @DefaultValue("1") int page) {
        int pageSize = 10;
        List<String> results = logService.searchLogs(query, page, pageSize);
        int totalResults = logService.getTotalResults(query);
        boolean hasNextPage = page * pageSize < totalResults;
    
        return searchResultsTemplate
                .data("results", results)
                .data("query", query)
                .data("page", page)
                .data("hasNextPage", hasNextPage)
                .data("prevPage", page > 1 ? page - 1 : null)
                .data("nextPage", hasNextPage ? page + 1 : null);
    }
    
    @GET
    @Path("/devices/{deviceName}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getDeviceLogsByName(@PathParam("deviceName") String deviceName,
                                                @QueryParam("page") @DefaultValue("1") int page) {
        int pageSize = 10;
        List<String> logs = logService.getDeviceLogsByName(deviceName, page, pageSize);
        int totalResults = logService.getTotalDeviceLogsCountByName(deviceName);
        boolean hasNextPage = page * pageSize < totalResults;
    
        // Prepare the data for the main page load
        return devices
                .data("deviceName", deviceName)
                .data("logs", logs)
                .data("page", page)
                .data("hasNextPage", hasNextPage);
    }
    
    @GET
    @Path("/devices/{deviceName}/page")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getDeviceLogsByPage(@PathParam("deviceName") String deviceName,
                                                @QueryParam("page") int page) {
        int pageSize = 10;
        List<String> logs = logService.getDeviceLogsByName(deviceName, page, pageSize);
        int totalResults = logService.getTotalDeviceLogsCountByName(deviceName);
        boolean hasNextPage = page * pageSize < totalResults;
    
        // Prepare the data only for pagination updates (log entries and pagination links)
        return logsPage
                .data("deviceName", deviceName)
                .data("logs", logs)
                .data("page", page)
                .data("hasNextPage", hasNextPage)
                .data("prevPage", page > 1 ? page - 1 : null)
                .data("nextPage", hasNextPage ? page + 1 : null);
    }
        
}
