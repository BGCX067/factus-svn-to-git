package ru.factus;

import java.io.IOException;
import java.io.File;

import ru.factus.services.FetcherService;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 04.05.2008 19:58:03
 */
public class FetcherConfiguration {
  private FetcherService fetcherService;
  private String cacheStoreDir;
  private int executeCount;
  private int pollingPeriod;//in sec

  public void doFetching() throws IOException, ClassNotFoundException, InterruptedException {
    final File cacheDir = new File(cacheStoreDir);

    fetcherService.restoreFeedInfoCache(cacheDir);
    for (int i = 0; i < executeCount; i++) {      
      fetcherService.makeFetch();
      Thread.currentThread().sleep(pollingPeriod*1000);
    }
    fetcherService.storeFeedInfoCache(cacheDir);
  }

  public String getCacheStoreDir() {
    return cacheStoreDir;
  }

  public void setCacheStoreDir(String cacheStoreDir) {
    this.cacheStoreDir = cacheStoreDir;
  }

  public int getExecuteCount() {
    return executeCount;
  }

  public void setExecuteCount(int executeCount) {
    this.executeCount = executeCount;
  }

  public FetcherService getFetcherService() {
    return fetcherService;
  }

  public void setFetcherService(FetcherService fetcherService) {
    this.fetcherService = fetcherService;
  }

  public int getPollingPeriod() {
    return pollingPeriod;
  }

  public void setPollingPeriod(int pollingPeriod) {
    this.pollingPeriod = pollingPeriod;
  }
}
