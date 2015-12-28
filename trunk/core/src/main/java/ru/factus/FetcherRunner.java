package ru.factus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FetcherRunner {
  protected static final Log log = LogFactory.getLog(FetcherRunner.class);
  protected static ClassPathXmlApplicationContext context;

  public static void setUp(){
    DOMConfigurator.configure(FetcherRunner.class.getResource("../../log4j.xml"));
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

  public static void tearDown() throws Exception {
    if (context != null) {
      log.info("Closing down the spring context");
      context.destroy();
    }
  }

  protected static <T> T getBean(String name) {
    return (T) context.getBean(name);
  }

  public static void main(String[] args) throws Exception {
    setUp();

    FetcherConfiguration fc = getBean("configuration");
    fc.doFetching();

    tearDown();
  }

}
