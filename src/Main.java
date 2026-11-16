  import com.joaovitor.log.utils.RegexLogParser;
  import com.joaovitor.log.service.LogService;
  import com.joaovitor.log.utils.LogParser;
  import com.joaovitor.log.view.LogView;

  public class Main {
    public static void main(String[] args) {

      LogParser lParser = new RegexLogParser();
      LogService service = new LogService(lParser);

      LogView view = new LogView(service);

      view.start();
    }
  }
