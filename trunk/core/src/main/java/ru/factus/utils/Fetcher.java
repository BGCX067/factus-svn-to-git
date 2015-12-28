package ru.factus.utils;

import java.util.TimerTask;

import ru.factus.services.FetcherService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 23.04.2008 18:06:01
 */
public class Fetcher extends TimerTask {
  protected static final Log log = LogFactory.getLog(Fetcher.class);

  private FetcherService service;
  private int count = 0;
  private int executeCount = 100;

  public void run(){
    try {
      log.info("Next fetcher run ("+(count++)+")...");
      service.makeFetch();
      log.info("Done.");
    } catch (Exception e) {
      log.error("Fetch problem: ", e);
    }

    /*if(count>executeCount&&executeCount!=-1)
      this.cancel();*/  
  }


  public void setService(FetcherService service) {
    this.service = service;
  }

  public int getExecuteCount() {
    return executeCount;
  }

  public void setExecuteCount(int executeCount) {
    this.executeCount = executeCount;
  }
}
