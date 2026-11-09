import model.LogEntry;
import service.LogService;
import utils.LogParser;
import utils.RegexLogParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) {

    LogParser lParser = new RegexLogParser();
    LogService lService = new LogService(lParser);

    lService.processFile("logs/app.log");

    //System.out.println(lService.searchByLevel("INFO"));

    System.out.println(lService.searchByKeyword("servidor"));
    System.out.println(lService.searchByKeyword("cpu"));
  }
}
