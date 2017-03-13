/**
 * @工程名称：esRestClient
 * @程序包名：com.ztbrothers.support.elasticsearch.enums
 * @程序类名：indices.java
 * @创建日期：2017年2月28日
 */
package com.ztbrothers.secure.support.elasticsearch.enums;

/**
 * @功能说明： elasticsearch method 类型       <BR>
 * @创建人员：LeoLu<BR>
 * @创建日期：2017年2月28日<BR>
 * @变更记录：<BR>
 * 1、2017年2月28日 LeoLu 更新
 */
public enum EsMethodType {
  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE"),
  OPTIONS("OPTIONS"),
  HEAD("HEAD"), 
  TRACK("TRACK"),
  CONNECT("CONNECT");
  
  private String name;

  private EsMethodType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  
}
