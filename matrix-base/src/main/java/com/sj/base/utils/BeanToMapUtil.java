package com.sj.base.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class BeanToMapUtil {

  public static boolean isMapClass(String clazzName) {
		if (clazzName == null) {
			return true;
		}
    Class<?> clazz = null;
    try {
      clazz = Class.forName(clazzName);
      if (clazz.equals(HashMap.class) || clazz.equals(Map.class)) {
        return true;
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * 将一个 Map 对象转化为一个 JavaBean
   *
   * @param clazzName 要转化的类型字符串
   * @param map       包含属性值的 map
   * @return 转化出来的 JavaBean 对象
   * @throws IntrospectionException    如果分析类属性失败
   * @throws IllegalAccessException    如果实例化 JavaBean 失败
   * @throws InstantiationException    如果实例化 JavaBean 失败
   * @throws InvocationTargetException 如果调用属性的 setter 方法失败
   */
  public static Object convertMap(String clazzName, Map<String, Object> map)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
    Class<?> clazz = Class.forName(clazzName);
    return convertMap(clazz, map);
  }

  /**
   * 将一个 Map 对象转化为一个 JavaBean
   *
   * @param type 要转化的类型
   * @param map  包含属性值的 map
   * @return 转化出来的 JavaBean 对象
   * @throws IntrospectionException    如果分析类属性失败
   * @throws IllegalAccessException    如果实例化 JavaBean 失败
   * @throws InstantiationException    如果实例化 JavaBean 失败
   * @throws InvocationTargetException 如果调用属性的 setter 方法失败
   */
  public static Object convertMap(Class<?> type, Map<String, Object> map)
      throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
    BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
    Object obj = type.newInstance(); // 创建 JavaBean 对象

    // 给 JavaBean 对象的属性赋值
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      Object value = null;
      if (map.containsKey(propertyName)) {
        // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
        value = map.get(propertyName);
      }
      if (value == null) {
        String fpn = getDBField(propertyName);
        value = map.get(fpn);
        if (value == null) {
          value = map.get(fpn.toLowerCase());
        }
      }
      if (value != null) {
        if (value.getClass().equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
        } else {
          if (Integer.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            if (StringUtils.isNotEmpty((String) value)) {
              value = ParseUtils.parseInteger((String) value);
            } else {
              value = null;
            }
          } else if (Date.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            if (value instanceof String && StringUtils.isNotBlank(String.class.cast(value))) {
              if (!StringUtils.isNumeric(String.class.cast(value))) {
                if (String.class.cast(value).length() > 10) {
                  try {
                    value = DateUtil
                        .convertStringToDate("yyyy-MM-dd HH:mm:ss", String.class.cast(value));
                  } catch (ParseException e) {
                  }
                } else {
                  try {
                    value = DateUtil.convertStringToDate("yyyy-MM-dd", String.class.cast(value));
                  } catch (ParseException e) {
                  }
                }
              } else {

              }
            } else {
              long time = 0;
              if (value instanceof String) {
                time = Long.parseLong(String.class.cast(value)) * 1000l;
              } else if (value instanceof Integer) {
                time = Integer.class.cast(value).longValue() * 1000l;
              } else if (value instanceof Long) {
                time = Long.class.cast(value) * 1000l;
              } else {
                continue;
              }
              Calendar calendar = Calendar.getInstance();
              calendar.setTimeInMillis(time);
              value = calendar.getTime();
            }
          } else if (String.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            value = value.toString();
          } else if (Long.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            if (StringUtils.isNotEmpty(String.valueOf(value))) {
              value = Long.parseLong(String.valueOf(value));
            } else {
              value = null;
            }
          } else if (Short.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            if (StringUtils.isNotEmpty(String.valueOf(value))) {
              value = Short.parseShort(String.valueOf(value));
            } else {
              value = null;
            }
          } else if (BigDecimal.class.equals(descriptor.getWriteMethod().getParameterTypes()[0])) {
            if (StringUtils.isNotEmpty(String.valueOf(value))) {
              value = new BigDecimal(String.valueOf(value));
            } else {
              value = null;
            }
          }
        }
        Object[] args = new Object[1];
        args[0] = value;
        descriptor.getWriteMethod().invoke(obj, args);
      }
    }
    return obj;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static String getDBField(String propertyName) {
    List charList = new ArrayList();
    char[] chars = propertyName.toCharArray();
    for (int j = 0; j < chars.length; j++) {
      char c = chars[j];
      if (c >= 'A' && c <= 'Z') {
        charList.add('_');
        charList.add(c);
      } else {
        charList.add(c);
      }
    }
    char[] tc = new char[charList.size()];
    for (int j = 0; j < charList.size(); j++) {
      tc[j] = (char) charList.get(j);
    }
    String fpn = new String(tc, 0, tc.length).toUpperCase();
    return fpn;
  }

  /**
   * 将一个 JavaBean 对象转化为一个 Map
   *
   * @param bean 要转化的JavaBean 对象
   * @return 转化出来的 Map 对象
   * @throws IntrospectionException    如果分析类属性失败
   * @throws IllegalAccessException    如果实例化 JavaBean 失败
   * @throws InvocationTargetException 如果调用属性的 setter 方法失败
   */
  public static Map<String, Object> convertBean(Object bean)
      throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    Class<?> type = bean.getClass();
    Map<String, Object> returnMap = new HashMap<>();
    BeanInfo beanInfo = Introspector.getBeanInfo(type);

    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    for (int i = 0; i < propertyDescriptors.length; i++) {
      PropertyDescriptor descriptor = propertyDescriptors[i];
      String propertyName = descriptor.getName();
      if (!propertyName.equals("class")) {
        Method readMethod = descriptor.getReadMethod();
        Object result = readMethod.invoke(bean, new Object[0]);
        if (result != null) {
          returnMap.put(propertyName, result);
        } else {
          returnMap.put(propertyName, "");
        }
      }
    }
    return returnMap;
  }
}