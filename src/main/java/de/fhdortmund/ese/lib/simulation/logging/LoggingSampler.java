package de.fhdortmund.ese.lib.simulation.logging;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.LoggingFilter;

@LoggingFilter(name = "logging-sampler")
public class LoggingSampler implements Filter{

    @ConfigProperty(name = "logging.sampler.rate", defaultValue = "1")
    double samplingRate;
    
    @Override
    public boolean isLoggable(LogRecord record) {
        return ThreadLocalRandom.current().nextDouble() < samplingRate;
    }

}
