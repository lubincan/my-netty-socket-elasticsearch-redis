/**
 * @工程名称：esRestClient
 * @程序包名：com.ztbrothers.secure.support.elasticsearch.resclient
 * @程序类名：EsRestClient.java
 * @创建日期：2017年2月23日
 */
package com.ztbrothers.secure.support.elasticsearch.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import com.ztbrothers.secure.support.elasticsearch.enums.EsMethodType;

/**
 * @功能说明： 编写Elasticsearch restClient客户的端工具类   <BR>
 * @创建人员：LeoLu<BR>
 * @创建日期：2017年2月23日<BR>
 * @变更记录：<BR>
 * 1、2017年2月23日 LeoLu 更新
 */
public class EsRestClient {
  private String esUser;
  private String esPassword;
  private String esHost;
  private int esSport;
  private Integer esConnectTimeout;
  private Integer esSocketTimeout;
  private Integer esRetryTimeoutMillis;
  private Integer esThreadCount;
  private static RestClient restClient;
  private BasicHeader basicHeader = new BasicHeader("Content-Type", "application/json");

  /**
   * @构造函数 
   */
  public EsRestClient() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @函数名称：getRestClient<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明： 获取restClient对象<br>
   * @参数说明： <br>
   * @返回说明：RestClient
   */
  public RestClient getRestClient(){

    /**如果restClient对象已经存在，则直接返回*/
    if (restClient != null) return restClient;
    
    RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(esHost, esSport, "http")); 
    
    /**设置Elasticsearch账号密码信息*/
    if (!isEmptyStr(esUser) && !isEmptyStr(esPassword)) {
      final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esUser, esPassword));
      restClientBuilder.setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider));
    }
    
    /**设置el连接超时时间*/
    if (null != esConnectTimeout) {
      restClientBuilder.setRequestConfigCallback(builder-> builder.setConnectTimeout(esConnectTimeout));
    }
    
    /**设置es socket超时时间*/
    if (null != esSocketTimeout) {
      restClientBuilder.setRequestConfigCallback(builder-> builder.setSocketTimeout(esSocketTimeout));
    }
    
    /**设置es重试动作超时时间*/
    if (null != esRetryTimeoutMillis) {
      restClientBuilder.setMaxRetryTimeoutMillis(esRetryTimeoutMillis);
    }
    
    /**设置es一步操作线程数*/
    if (null != esThreadCount) {
      restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(esThreadCount).build()));

    }
    return restClientBuilder.build();
  }
  
  /**
   * @函数名称：isEmptyStr<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明：判断字符串是否为空 <br>
   * @参数说明：str String 输入字符串 <br>
   * @返回说明：boolean
   */
  private static boolean isEmptyStr(String str){
    if (str !=null && str.length() >0) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * @函数名称：getIndex<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明：不带参数的index查询 <br>
   * @参数说明： reqPath String 请求目录例如/idex/type/id<br>
   * @返回说明：String
   */
  public String getIndexStr(String reqPath){
    
    try {
      return converIndexMsg(getIndex(reqPath).getEntity().getContent());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * @函数名称：getIndex<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明：不带参数的index查询  <br>
   * @参数说明：reqPath String 请求目录例如/idex/type/id <br>
   * @返回说明：Response
   */
 public Response getIndex(String reqPath, String dsl, Map<String, String> params){
   try {
     if (isEmptyStr(dsl)) return getRestClient().performRequest(EsMethodType.GET.getName(), reqPath, basicHeader);
     if (null == params) {
       params = Collections.<String, String>emptyMap();
     }
    return getRestClient().performRequest(EsMethodType.GET.getName(), reqPath, params, new NStringEntity(dsl, ContentType.APPLICATION_JSON), basicHeader);
    
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
   return null;
  }
  
 public Response getIndex(String reqPath, String dsl){
   return getIndex(reqPath, dsl, null);
 }
 
 /**
  * @函数名称：getIndex<br>
  * @创建日期：2017年2月23日<br>
  * @功能说明：index查询 <br>
  * @参数说明：reqPath String 请求目录例如/idex/type/id  <br>
  * @参数说明：params Map 请求参数  <br>
  * @返回说明：Response
  */
 public Response getIndex(String reqPath, Map<String, String> params){
   return getIndex(reqPath, null, params);
 }
 
 /**
  * @函数名称：getIndex<br>
  * @创建日期：2017年2月23日<br>
  * @功能说明：不带参数的index查询 <br>
  * @参数说明： reqPath String 请求目录例如/idex/type/id <br>
  * @返回说明：Response
  */
 public Response getIndex(String reqPath){
   return getIndex(reqPath, null, null);
 }
  /**
   * @函数名称：getIndexMsg<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明： InputStream转换为string<br>
   * @参数说明：  input InputStream 数据输入流<br>
   * @返回说明：String
   */
  public String converIndexMsg(InputStream input){
    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
    StringBuilder sb = new StringBuilder(); 
    String line = null;   
    try{
      
      while ((line = reader.readLine()) != null) {      
               sb.append(line + "\n");      
             } 
      
    }catch (Exception e) {
      e.printStackTrace();
      
    } finally {      
      
      try {      
        input.close();
        
       } catch (IOException e) {      
           e.printStackTrace();      
         }      
      }   
    
    return sb.toString();
  }

  /***
   * @函数名称：index<br>
   * @创建日期：2017年2月23日<br>
   * @功能说明：创建索引 <br>
   * @参数说明：reqPath String 索引目录 <br>
   * @参数说明： dls String DSL语句<br>
   * @返回说明：Response
   */
  public Response index(String reqPath, String dsl){
    try {
     return getRestClient().performRequest(EsMethodType.PUT.getName(),reqPath, Collections.<String, String>emptyMap(), new NStringEntity(dsl, ContentType.APPLICATION_JSON), basicHeader);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public void setEsUser(String esUser) {
    this.esUser = esUser;
  }

  public void setEsPassword(String esPassword) {
    this.esPassword = esPassword;
  }

  public void setEsHost(String esHost) {
    this.esHost = esHost;
  }

  public void setEsSport(int esSport) {
    this.esSport = esSport;
  }

  public void setEsConnectTimeout(Integer esConnectTimeout) {
    this.esConnectTimeout = esConnectTimeout;
  }

  public void setEsSocketTimeout(Integer esSocketTimeout) {
    this.esSocketTimeout = esSocketTimeout;
  }

  public void setEsRetryTimeoutMillis(Integer esRetryTimeoutMillis) {
    this.esRetryTimeoutMillis = esRetryTimeoutMillis;
  }

  public void setEsThreadCount(Integer esThreadCount) {
    this.esThreadCount = esThreadCount;
  }
  
  

}
