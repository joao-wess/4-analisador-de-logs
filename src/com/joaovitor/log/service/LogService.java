package com.joaovitor.log.service;

import com.joaovitor.log.exception.ArquivoLogException;
import com.joaovitor.log.model.LogEntry;
import com.joaovitor.log.utils.LogParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
      throw new ArquivoLogException("O arquivo de log especificado não foi encontrado ou está vazio. " + e.getMessage());
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

  //return logs
  public List<LogEntry> getLogs() {
    return logs;
  }

  public Map<String, Long> getSummary() {
    return logs.stream()
            .collect(Collectors.groupingBy(LogEntry::logLevel, Collectors.counting()));
  }

  public void exportReport(String destinationPath) {
    //1. Cria o quadro branco
    StringBuilder sb = new StringBuilder();

    sb.append("------------ RELATÓRIO DE LOGS -------------- \n");
    sb.append("Total de logs: ").append(logs.size()).append("\n\n");

    sb.append("Resumo por Nível:\n");

    //2. Pega o Dicionário de Resumo (ex: {"INFO": 50, "ERROR": 12})
    Map<String, Long> summary = getSummary();

    // 3. Transforma em lista e faz um loop no Dicionário para escrever cada nível no quadro
    for(Map.Entry<String, Long> row : summary.entrySet()) {
      sb.append(row.getKey()).append(": ").append(row.getValue()).append("\n");
    }

    // 4. tira a "foto" do quadro e converte em String
    String finalContent = sb.toString();

    // 5. prepara o endereço do HD
    Path path = Path.of(destinationPath);

    try {
      if (path.getParent() != null) {
        Files.createDirectories(path.getParent());
      }

      Files.writeString(path, finalContent);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar o relatório: " + e.getMessage());
    }
  }
}
