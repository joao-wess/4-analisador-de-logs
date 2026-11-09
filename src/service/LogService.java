package service;

import model.LogEntry;
import utils.LogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LogService{
  //list for storage logs line
  private List<LogEntry> logs = new ArrayList<>();

  private LogParser parser;

  public LogService(LogParser parser) {
    this.parser = parser;
  }

  //process file and treatment error
  public void processFile(String filePath) {
    Path caminho = Path.of(filePath);

    try (Stream<String> rows = Files.lines(caminho)){
      this.logs = rows.map(row -> {
        try {
          return parser.parse(row);
        } catch (IllegalArgumentException e) {
          return null;
        }
      })
              .filter(log -> log != null)
              .toList();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //search by level
  public List<LogEntry> searchByLevel(String level) {
    return logs.stream()
            .filter(log -> log.logLevel().equalsIgnoreCase(level))
            .toList();
  }

  //search by keyword
  public List<LogEntry> searchByKeyword(String message) {
    return logs.stream()
            .filter(log -> log.message().toLowerCase().contains(message.toLowerCase()))
            .toList();
  }
}
