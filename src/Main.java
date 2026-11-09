import com.joaovitor.log.RegexLogParser;
import com.joaovitor.log.service.LogService;
import com.joaovitor.log.utils.LogParser;

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
