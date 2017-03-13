package com.ztbrothers.secure.netty;

import java.util.Random;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class SocketMetronService {
  private static SocketIOServer nettyService = null;
  
  public static void initSocket(){
    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setPort(9092);
    nettyService = new SocketIOServer(config);
    nettyService.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
        @Override
        public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
          nettyService.getBroadcastOperations().sendEvent("chatevent", data);
          //nettyService.getBroadcastOperations().sendEvent("totalLog", new Random().nextInt(1000));
        }
    });
    
    nettyService.start();
  }

  private SocketMetronService() {
    super();
    // TODO Auto-generated constructor stub
  }

  public static SocketIOServer getNettyService(){
    if (null == nettyService) initSocket();
    return nettyService;
    
  }
  
}
