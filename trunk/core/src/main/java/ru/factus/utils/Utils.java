package ru.factus.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 11.07.2008 16:28:58
 */
public class Utils {
  public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yy HH:mm");

  public static String formatTime(Object date) {
    if(date instanceof Calendar){
      Calendar c = (Calendar) date;
      return SIMPLE_DATE_FORMAT.format(c.getTime());
    }
    return SIMPLE_DATE_FORMAT.format(date);
  }
}
