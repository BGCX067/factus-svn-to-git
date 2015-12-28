package ru.factus.services.impl;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.fetcher.*;
import com.sun.syndication.fetcher.impl.*;
import ru.factus.bo.fetch.Feed;
import ru.factus.bo.fetch.FetchedData;
import ru.factus.dao.fetch.FeedDAO;
import ru.factus.dao.fetch.FetchedDataDAO;
import ru.factus.services.AbstractServiceImpl;
import ru.factus.services.FetcherService;


/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 17.04.2008 18:06:41
 */
@Service("fetcherService") 
public class FetcherServiceImpl extends AbstractServiceImpl implements FetcherService {
  public static final String FEED_CACHE_FILE_NAME = "feed.cache";

  private FetchedDataDAO fetchedDAO;
  private FeedDAO feedDAO;
  private FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();

  public void storeFeedInfoCache(File storeDir) throws IOException {
    storeDir.mkdirs();
    final ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(new File(storeDir, FEED_CACHE_FILE_NAME)));
    oout.writeObject(feedInfoCache);
    oout.close();
  }

  public void restoreFeedInfoCache(File storeDir) throws IOException, ClassNotFoundException {
    final File file = new File(storeDir, FEED_CACHE_FILE_NAME);
    if(file.exists()){
      final ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
      feedInfoCache = (FeedFetcherCache) oin.readObject();
      oin.close();
    }
    else
      log.info("Feed Cache no found.");
  }

  public void makeFetch() {
    final List<Feed> feeds = feedDAO.findAll();

    final FeedFetcher fetcher = new HttpURLFeedFetcher(feedInfoCache);
    final FetchContext fetchContext = new FetchContext();
    fetcher.addFetcherEventListener(new FetcherEventListenerImpl(fetchContext));

    for (Feed feed : feeds) {
      try {
        final URL feedUrl = new URL(feed.getLink());
        fetchContext.setCurrentFeed(feed);
        fetcher.retrieveFeed(feedUrl);
      } catch (Throwable e) {
        log.error("Ошибка при разборе потока \""+feed.getLink()+"\"", e);
      }
    }
  }

  public void store(Feed feed) {
    feedDAO.persist(feed);
  }

  @Resource
  public void setFetchedDAO(FetchedDataDAO fetchedDAO) {
    this.fetchedDAO = fetchedDAO;
  }

  @Resource
  public void setFeedDAO(FeedDAO feedDAO) {
    this.feedDAO = feedDAO;
  }

  class FetcherEventListenerImpl implements FetcherListener {
    private FetchContext context;

    public FetcherEventListenerImpl(FetchContext fetchContext) {
      this.context = fetchContext;
    }

    public void fetcherEvent(FetcherEvent event) {
      String eventType = event.getEventType();
      if (FetcherEvent.EVENT_TYPE_FEED_POLLED.equals(eventType)) {

      } else if (FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals(eventType)) {
        final SyndFeed syndFeed = event.getFeed();
        final Feed feed = context.getCurrentFeed();

        final List<SyndEntry> entries = syndFeed.getEntries();
        if(!entries.isEmpty()){
          FetchedData data = null;
          for (SyndEntry entry : entries){
            final Calendar publishedDate = convert(entry.getPublishedDate());

            if(publishedDate ==null)
              continue;

            Number count = (Number) fetchedDAO.getFilterContext().add(Restrictions.eq("link", entry.getLink())).setProjection(Projections.count("id")).getCriteria().uniqueResult();
            if(count.intValue() > 0)
              continue;

            final SyndContent content = entry.getDescription();
            if(content==null) //Если нет новости, то и не сохраняем её
              continue;

            final String value = content.getValue();
            if(value == null || value.length() == 0) //Если нет текста новости, то и не сохраняем её
              continue;

            data = new FetchedData(feed);
            data.setLink(entry.getLink());
            data.setTitle(entry.getTitle());



            data.setText(value);
            data.setFeed(feed);
            data.setDate(publishedDate);
            data.setDateCreate(Calendar.getInstance());


            fetchedDAO.persist(data);
          }
          fetchedDAO.getSession().flush();
          if(data!=null){
            feed.setLastVisitTime(data.getDate());
            feedDAO.merge(feed);
          }
        }
      } else if (FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals(eventType)) {

      }
    }
  }

  public class FetchContext {
    private Feed currentFeed;

    public Feed getCurrentFeed() {
      return currentFeed;
    }

    public void setCurrentFeed(Feed currentFeed) {
      this.currentFeed = currentFeed;
    }
  }

  public static Calendar convert(Date date){
    if(date == null)
      return null;

    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar;
  }

}
