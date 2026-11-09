package com.joaovitor.log.utils;

import com.joaovitor.log.model.LogEntry;

public interface LogParser {
  LogEntry parse(String logLine);
}
