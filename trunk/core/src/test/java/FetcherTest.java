import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.factus.bo.fetch.Feed;
import ru.factus.services.FetcherService;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 10.04.2008 9:59:52
 */
public class FetcherTest {

  protected static final Log log = LogFactory.getLog(FetcherTest.class);
  protected static ClassPathXmlApplicationContext context;

  @BeforeClass
  public static void setUp(){
    //PropertyConfigurator.configure(TEST_HOME+"log4j.properties");
    DOMConfigurator.configure("core/src/main/resources/log4j.xml");
    log.info("SetUp...");
    try {
      context = new ClassPathXmlApplicationContext(new String[]{
              "hibernate-spring.xml",
              "services.xml"
      });
    } catch (Exception e) {
      log.error("Error loading context:",e);
    }
  }

  @AfterClass
  public static void tearDown() throws Exception {
    if (context != null) {
      log.info("Closing down the spring context");
      context.destroy();
    }
  }

  protected <T> T getBean(String name) {
    return (T) context.getBean(name);
  }

  String[] urls = new String[]{
          "http://news.yandex.ru/Russia/index.rss",
          "http://www.klerk.ru/xml/index.xml",
          "http://static.feed.rbc.ru/rbc/internal/rss.rbc.ru/rbcdaily.ru/mainnews.rss",
          "http://www.rosbalt.ru/backend/news.xml",
          "http://www.rambler.ru/news/top/rss.html",
          "http://img.lenta.ru/r/EX/import.rss",
          "http://www.novoteka.ru/rss/Economics",
          "http://www.polit.ru/rss/index.xml",
          "http://www.nns.ru/news.rss",
          "http://www.gazeta.ru/export/rss/politics.xml",
          "http://www.gazeta.ru/export/rss/business.xml",
          "http://news.eprst.ru/rss.php",
          "http://www.prime-tass.ru/rss/rushot_rss.xml",
          "http://www.prime-tass.ru/rss/comm_rss.xml",
          "http://www.prime-tass.ru/rss/articles_rss.xml",
          "http://www.regnum.ru/rss/polit.xml",
          "http://www.regnum.ru/rss/economy.xml",
          "http://www.akm.ru/rus/rss/rss2.0.xml"
  };

  @Test
  public void testFetcher() throws Exception {
    initFeeds();
/*
    final FetcherService fetcherService = getBean("fetcherService");
    fetcherService.makeFetch();*/

    Thread.sleep(10000000);
  }

  private void initFeeds() {
    FetcherService service = getBean("fetcherService");
    for (String url : urls) {
      try {
        service.store( new Feed(url));
      } catch (Exception e) {
        log.info("Error adding URL:", e);
      }
    }
  }
}
