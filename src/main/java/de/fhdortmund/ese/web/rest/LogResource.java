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

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public String searchLogs(@QueryParam("query") String query, @QueryParam("page") @DefaultValue("1") int page) {
        int pageSize = 10;
        List<String> results = logService.searchLogs(query, page, pageSize);
        int totalResults = logService.getTotalResults(query);

        StringBuilder resultHtml = new StringBuilder("<div id='log-results'><ul>");
        for (String log : results) {
            resultHtml.append("<li>").append(log).append("</li>");
        }
        resultHtml.append("</ul>");
        
        // Pagination Controls
        if (page > 1) {
            resultHtml.append("<a hx-get='/logs/search?query=").append(query).append("&page=").append(page - 1)
                      .append("' hx-target='#log-results'>Previous</a>");
        }
        if (page * pageSize < totalResults) {
            resultHtml.append("<a hx-get='/logs/search?query=").append(query).append("&page=").append(page + 1)
                      .append("' hx-target='#log-results'>Next</a>");
        }
        resultHtml.append("</div>");
        
        return resultHtml.toString();
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

        // Pass the data to the template
        return devices
                .data("deviceName", deviceName)
                .data("logs", logs)
                .data("page", page)
                .data("hasNextPage", hasNextPage);
    }

}
