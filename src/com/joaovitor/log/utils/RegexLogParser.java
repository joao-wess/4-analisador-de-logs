package com.joaovitor.log.utils;

import com.joaovitor.log.model.LogEntry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexLogParser implements LogParser {
  //regra para compilar a minha regex
  private static final Pattern PATTERN = Pattern.compile("^\\[(.+?)] \\[(.+?)] (.+)$");

  //formato da nossa data
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public LogEntry parse(String logLine) {
    //detetive que analisa a linha de texto usando nosso regex
    Matcher matcher = PATTERN.matcher(logLine);

    if (matcher.matches()) {
      String dateString = matcher.group(1);
      String level = matcher.group(2);
      String message = matcher.group(3);

      LocalDateTime date = LocalDateTime.parse(dateString, FORMATTER);

      return new LogEntry(date, level, message);
    }

    throw new IllegalArgumentException("Linha de LOG inválida: " + logLine);
  }
}
