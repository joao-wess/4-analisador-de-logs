package model;

import java.time.LocalDateTime;

public record LogEntry(LocalDateTime date, String logLevel, String message) { }
