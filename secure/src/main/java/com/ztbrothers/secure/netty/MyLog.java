/**
 * @工程名称：netty-service
 * @程序包名：com.corundumstudio.socketio.demo
 * @程序类名：MyLog.java
 * @创建日期：2017年2月28日
 */
package com.ztbrothers.secure.netty;

/**
 * @功能说明：        <BR>
 * @创建人员：LeoLu<BR>
 * @创建日期：2017年2月28日<BR>
 * @变更记录：<BR>
 * 1、2017年2月28日 LeoLu 更新
 */
public class MyLog {

  /**
   * @构造函数 
   */
  public MyLog() {
    // TODO Auto-generated constructor stub
  }
  
  private String ip;
  private String requestUrl;
  private boolean successful;
  private long logTime;
  public String getIp() {
    return ip;
  }
  public String getRequestUrl() {
    return requestUrl;
  }
  public long getLogTime() {
    return logTime;
  }
  public void setIp(String ip) {
    this.ip = ip;
  }
  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }
  public void setLogTime(long logTime) {
    this.logTime = logTime;
  }
  public boolean isSuccessful() {
    return successful;
  }
  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }

  public String toJson(){
    return "{\"ip\":\""+getIp()+"\", \"requestUrl\":\""+getRequestUrl()+"\", \"logTime\":\""+getLogTime()+"\",\"successful\":"+isSuccessful()+"}";
  }
}
