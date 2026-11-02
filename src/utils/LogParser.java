package utils;

import model.LogEntry;

public interface LogParser {
  LogEntry parse(String logLine);
}
