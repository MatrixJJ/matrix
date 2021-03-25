package com.sj.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil implements Serializable {

  private static final long serialVersionUID = -292336323378065582L;
  private static final String CHAR_SET = "UTF-8";
  private static final String CONTENT_TYPE = "application/json";

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);


  public static String postData(String url, Map<String, String> params, String codePage)
      throws IOException {
    LOGGER.debug("请求地址：" + url);
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpPost httpPost = new HttpPost(url);
      List<NameValuePair> data = new ArrayList<>();
      if (params != null) {
        for (Entry<String, String> entry : params.entrySet()) {
          data.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
      }
      httpPost.setEntity(new UrlEncodedFormEntity(data, codePage));
      httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
      httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
      // 执行请求操作，并拿到结果（同步阻塞）
      CloseableHttpResponse response = httpClient.execute(httpPost);
      // 获取结果实体
      try {
        String body = "";
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          // 按指定编码转换结果实体为String类型
          body = EntityUtils.toString(entity, codePage);
        }
        EntityUtils.consume(entity);
        //	LOGGER.debug("响应数据：" + body);
        return body;
      } finally {
        response.close();
      }
      // 释放链接
    } finally {
      httpClient.close();
    }
  }

  /**
   * 请求接口
   *
   * @param requestUrl 接口请求地址
   * @param json
   * @return UTF-8解码返回数据
   * @throws IOException
   */
  public static JSONObject doPostRequestJSON(String requestUrl, String json) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost post = new HttpPost(requestUrl);
    JSONObject result = null;
    try {
      StringEntity reqEntity = new StringEntity(json, CHAR_SET);
      reqEntity.setContentEncoding(CHAR_SET);
      reqEntity.setContentType(CONTENT_TYPE);
      post.setEntity(reqEntity);
      CloseableHttpResponse res = httpClient.execute(post);
      LOGGER.info("http status is " + res.getStatusLine().getStatusCode());
      if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        try {
          HttpEntity entity = res.getEntity();
          // UTF-8解码返回数据
          String resultStr = EntityUtils.toString(entity, CHAR_SET);
          result = JSONObject.parseObject(resultStr);
          EntityUtils.consume(entity);
        } finally {
          res.close();
        }
      } else {
        result = new JSONObject();
        try {
          result.put("code", -1);
          result.put("msg", "系统异常");
        } catch (JSONException e1) {
        }
      }
    } catch (IOException e) {
      result = new JSONObject();
      try {
        result.put("code", -1);
        result.put("msg", "系统异常");
      } catch (JSONException e1) {
      }
      e.printStackTrace();
    } catch (JSONException e) {
      result = new JSONObject();
      try {
        result.put("code", -1);
        result.put("msg", "系统异常");
      } catch (JSONException e1) {
      }
      e.printStackTrace();
    } finally {
      httpClient.close();
    }
    return result;
  }

  public static String readReqStr(HttpServletRequest request) {
    BufferedReader reader = null;
    StringBuilder sb = new StringBuilder();
    try {
      reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != reader) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

  public static String getData(String url, Map<String, String> params) {
    LOGGER.info("请求地址：" + url);
    LOGGER.info("请求参数：" + JSON.toJSONString(params));
    String retStr = "";
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      URIBuilder uriBuilder = new URIBuilder(url);
      if (params != null) {
        for (Entry<String, String> entry : params.entrySet()) {
          uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }
      }
      HttpGet httpGet = new HttpGet(uriBuilder.build());
      // 执行get请求.
      CloseableHttpResponse response = httpClient.execute(httpGet);
      try {
        // 获取响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          // 打印响应内容
          retStr = EntityUtils.toString(entity);
          //LOGGER.info("响应数据：" + retStr);
        }
      } finally {
        response.close();
      }
    } catch (ParseException | IOException | URISyntaxException e) {
      e.printStackTrace();
    } finally {
      // 关闭连接,释放资源
      try {
        httpClient.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return retStr;
  }


  /**
   * 执行文件下载
   */
  public static void executeDownloadFile(String url, String localFilePath,
      Map<String, String> params) {
    CloseableHttpResponse response = null;
    InputStream in = null;
    FileOutputStream fout = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      URIBuilder uriBuilder = new URIBuilder(url);
      if (params != null) {
        for (Entry<String, String> entry : params.entrySet()) {
          uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }
      }
      HttpGet httpGet = new HttpGet(uriBuilder.build());
      response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity == null) {
        LOGGER.error("文件下载失败" + MapUtils.getString(params, "order_no"));
        return;
      }
      in = entity.getContent();
      File file = new File(localFilePath);
      fout = new FileOutputStream(file);
      int l;
      byte[] tmp = new byte[1024];
      while ((l = in.read(tmp)) != -1) {
        fout.write(tmp, 0, l);
      }
      // 将文件输出到本地
      fout.flush();
      EntityUtils.consume(entity);
    } catch (ParseException | IOException | URISyntaxException e) {
      e.printStackTrace();
    } finally {
      // 关闭低层流。
      if (fout != null) {
        try {
          fout.close();
        } catch (Exception e) {
        }
      }
      if (response != null) {
        try {
          response.close();
        } catch (Exception e) {
        }
      }
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (Exception e) {
        }
      }
    }
  }

}
