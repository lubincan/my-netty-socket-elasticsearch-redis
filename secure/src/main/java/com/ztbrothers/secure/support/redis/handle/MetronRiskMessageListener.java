package com.ztbrothers.secure.support.redis.handle;

import com.ztbrothers.secure.netty.ChatObject;
import com.ztbrothers.secure.netty.SocketMetronService;

public class MetronRiskMessageListener {

  public MetronRiskMessageListener() {
    // TODO Auto-generated constructor stub
  }
  public void handleMessage(String message) {
    // TODO Auto-generated method stub
   try {
     SocketMetronService.getNettyService().getBroadcastOperations().sendEvent("showEvent", message);
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
   
  }
}
