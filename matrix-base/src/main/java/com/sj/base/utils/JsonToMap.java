package com.sj.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author ShengJie.Wang
 * @date 2020-11-03 10:26
 */
public class JsonToMap {

  public static Map<String, Object> parseJson2Map(JSONObject json) {
    Map<String, Object> map =json;
    for(Map.Entry<String, Object> entry : map.entrySet()) {
      if(entry.getValue() instanceof JSONObject){
        map.put(entry.getKey(), parseJson2Map((JSONObject) entry.getValue() ));
      }
      if (entry.getValue() instanceof JSONArray) {
        List<Map<String, Object>> list = new ArrayList<>() ;
        Iterator<Object> it = ((JSONArray) entry.getValue()).iterator();
        while (it.hasNext()) {
          JSONObject json2 =(JSONObject) it.next();
          list.add(parseJson2Map(json2));
        }
        map.put(entry.getKey(), list);
      }
    }
    return map;
  }

}
