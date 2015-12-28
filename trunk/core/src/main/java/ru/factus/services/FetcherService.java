package ru.factus.services;

import java.io.File;
import java.io.IOException;

import ru.factus.bo.fetch.Feed;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 17.04.2008 18:05:36
 */
public interface FetcherService extends GenericService {

  void storeFeedInfoCache(File storeDir) throws IOException;

  void restoreFeedInfoCache(File storeDir) throws IOException, ClassNotFoundException;

  void makeFetch();

  void store(Feed feed);
}
