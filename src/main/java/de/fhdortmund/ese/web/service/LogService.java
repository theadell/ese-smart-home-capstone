package de.fhdortmund.ese.web.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogService {
    private final String logDirectory = "logs";

    public List<String> searchLogs(String query, int page, int pageSize) {
        try (Stream<String> lines = Files.lines(Paths.get(logDirectory, "app.log"))) {
            Pattern pattern = Pattern.compile(query);
            return lines.filter(line -> pattern.matcher(line).find())
                        .skip((page - 1) * pageSize)
                        .limit(pageSize)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public int getTotalResults(String query) {
        try (Stream<String> lines = Files.lines(Paths.get(logDirectory, "app.log"))) {
            Pattern pattern = Pattern.compile(query);
            return (int) lines.filter(line -> pattern.matcher(line).find()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

        public List<String> getDeviceLogs(int page, int pageSize) {
            return getLogsFromFile("devices.log", page, pageSize);
        }
    
        public int getTotalDeviceLogsCount() {
            return getTotalLogCount("devices.log");
        }
    
        public List<String> getEnergySourceLogs(int page, int pageSize) {
            return getLogsFromFile("energy_sources.log", page, pageSize);
        }
    
        public int getTotalEnergySourceLogsCount() {
            return getTotalLogCount("energy_sources.log");
        }
    
        private List<String> getLogsFromFile(String fileName, int page, int pageSize) {
            try (Stream<String> lines = Files.lines(Paths.get(logDirectory, fileName))) {
                return lines.skip((page - 1) * pageSize)
                            .limit(pageSize)
                            .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
                return List.of();
            }
        }
    
        private int getTotalLogCount(String fileName) {
            try (Stream<String> lines = Files.lines(Paths.get(logDirectory, fileName))) {
                return (int) lines.count();
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
    
        public List<String> getDeviceLogsByName(String deviceName, int page, int pageSize) {
            try (Stream<String> lines = Files.lines(Paths.get(logDirectory, "devices.log"))) {
                return lines.filter(line -> line.contains(deviceName))
                            .skip((page - 1) * pageSize)
                            .limit(pageSize)
                            .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
                return List.of();
            }
        }
    
        public int getTotalDeviceLogsCountByName(String deviceName) {
            try (Stream<String> lines = Files.lines(Paths.get(logDirectory, "devices.log"))) {
                return (int) lines.filter(line -> line.contains(deviceName)).count();
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }    
}
