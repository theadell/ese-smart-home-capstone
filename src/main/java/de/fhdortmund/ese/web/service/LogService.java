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
}
