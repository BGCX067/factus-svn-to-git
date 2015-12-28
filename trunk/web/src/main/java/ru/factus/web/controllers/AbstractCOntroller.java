package ru.factus.web.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.stereotype.Controller;

/**
 * @author <a href="mailto:ziman200@gmail.com">freeman</a>
 *         created 14.05.2008 14:29:15
 */

public class AbstractCOntroller {

  @InitBinder
  public void initBinder(WebDataBinder binder) {

  }


}
