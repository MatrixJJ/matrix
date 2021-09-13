package com.sj.daily.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ShengJie.Wang
 * @date 2021-04-23 14:41
 */
public class NewJava {

  // 使用 java 8 排序
  private void sortUsingJava8(List<String> names) {
    Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
  }

  public static void main(String args[]) {
    NewJava  test = new NewJava() ;
    List<String> names1 = new ArrayList<String>();
    names1.add("Google ");
    names1.add("Runoob ");
    names1.add("Taobao ");
    names1.add("Baidu ");
    names1.add("Sina ");
    test.sortUsingJava8(names1) ;
    System.out.println(names1);
    Arrays.asList( "a", "c", "b" ).forEach( ( String e ) -> System.out.println( e ) );
    ConcurrentHashMap<String,Object> map = new ConcurrentHashMap(8) ;
  }


}
