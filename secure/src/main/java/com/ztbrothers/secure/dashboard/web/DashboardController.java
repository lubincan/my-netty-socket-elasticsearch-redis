/**
 * @工程名称：secure
 * @程序包名：com.ztbrothers.secure.dashboard.web
 * @程序类名：DashboardController.java
 * @创建日期：2017年2月28日
 */
package com.ztbrothers.secure.dashboard.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztbrothers.secure.support.elasticsearch.restclient.EsRestClient;
import com.ztbrothers.secure.support.redis.dao.RedisPushDAOImpl;

/**
 * @功能说明：        <BR>
 * @创建人员：LeoLu<BR>
 * @创建日期：2017年2月28日<BR>
 * @变更记录：<BR>
 * 1、2017年2月28日 LeoLu 更新
 */
@Controller
@RequestMapping("/dashboard/")
public class DashboardController {
   @Autowired
   private EsRestClient esRestClient;
   
   @Autowired
   private RedisPushDAOImpl redisPush;
  /**
   * @构造函数 
   */
  public DashboardController() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @函数名称：main<br>
   * @创建日期：2017年2月28日<br>
   * @功能说明： <br>
   * @参数说明： <br>
   * @返回说明：void
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  @RequestMapping("test")
  public @ResponseBody String test(){
    String msg = esRestClient.getIndexStr("/snort_index_2017.02.17.17/snort_doc/AVpLfEba4caoqlxpNo1M?pretty");
    return msg;
  }
  
  @RequestMapping("push")
  public void pushRedis(){
    redisPush.sendMessage("你好北京，我是来自redis通道推送的消息");
  }
}
