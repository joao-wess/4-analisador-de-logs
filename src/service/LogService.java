package service;

import utils.LogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class LogService{
  private LogParser parser;

  public LogService(LogParser parser) {
    this.parser = parser;
  }

  public void processFile(String filePath) {
    Path caminho = Path.of(filePath);

    try (Stream<String> rows = Files.lines(caminho)){
      rows.map(row -> {
        try {
          return parser.parse(row);
        } catch (IllegalArgumentException e) {
          System.out.println("Linha inválida/corrompida " + e.getMessage());
          return null;
        }
      })
              .filter(log -> log != null)
              .forEach(log -> System.out.println(log));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
