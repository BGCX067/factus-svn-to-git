package ru.factus.services;

import ru.factus.AbstractEntity;

/**
 * @author <a href="mailto:artifex.i@gmail.com">artifex</a>
 *         created: 19.10.2007 16:00:11
 */
public interface GenericService {
  <T> T get(Class<T> clazz, Long id);
}
