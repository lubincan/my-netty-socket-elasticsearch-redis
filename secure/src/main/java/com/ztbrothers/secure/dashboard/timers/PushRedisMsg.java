package com.ztbrothers.secure.dashboard.timers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ztbrothers.secure.netty.MyLog;
import com.ztbrothers.secure.support.elasticsearch.restclient.EsRestClient;
import com.ztbrothers.secure.support.redis.dao.RedisPushDAOImpl;

import net.sf.json.JSONObject;
@Component
public class PushRedisMsg {
  @Autowired
  private RedisPushDAOImpl redisPush;
  @Autowired
  private EsRestClient esRestClient;
  private static int i = 0, j = 0;
  public PushRedisMsg() {
    // TODO Auto-generated constructor stub
   
  }

  @PostConstruct
  @Async
  public void timerPushRedis(){
    try{
      String ip = "192.168.";
      Random random = new Random();
      String [] urls = {"http://www.baidu.com","http://blog.csdn.net/","http://mvnrepository.com/","https://www.elastic.co","http://chachaba.com","http://taobao.com","http://www.jd.com"};
      String[] value = { "网站遭到攻击了", "网站首页被人篡改了", "网站数据库访问不到了", "网站遭到病毒攻击", "网页打不开了" };
      String[] valueResult = { "被黑客攻击成功", "成功阻截黑客攻击" };
      MyLog logMsg = new MyLog();
      logMsg.setIp(ip+random.nextInt(255)+"."+ random.nextInt(255));
      logMsg.setLogTime(System.currentTimeMillis());
      if (i > urls.length-1) {
        i = 0;
      }
      
      if (j > value.length - 1) {
        j = 0;
      }
      
      logMsg.setRequestUrl(urls[i]);
      logMsg.setSuccessful(true);
      redisPush.sendMessage(logMsg.toJson());
      
      Thread.sleep(500);
      redisPush.sendMessage("zt_risk", showEventLogStr(value[j], valueResult[random.nextInt(2)]));
      Thread.sleep(200);
      if (i> 5) {
        String msg = esRestClient.getIndexStr("/bro_index_2017*/_search?size=1&from="+i);
        redisPush.sendMessage(msg);
      }

      i++;
      j++;
    }catch (Exception e) {
      e.printStackTrace();
    }
   
  }
  
  public String showEventLogStr(String message, String showEventResult) {

    String address = "192.168.";
    Random random = new Random();
    String ip = address + random.nextInt(255) + "." + random.nextInt(255);
    return new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\t\t\t" + "{\"ip\":" + ip
        + "，\t\t\t\"eventTime\"："
        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
        + "，\t\t\t\"message\"：" + message + "，\t\t\t\"showEventResult\":" + showEventResult
        + "}";
  }
}
