/**
 * Copyright(c) Yystar Technology Co.,Ltd
 * project：redis
 * package：com.mkfree.redis.pub
 * fileName：MessageDelegateListenerImpl.java
 * date：2015年12月25日
 */
package com.ztbrothers.secure.support.redis.handle;

import java.util.Random;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import com.ztbrothers.secure.netty.ChatObject;
import com.ztbrothers.secure.netty.SocketMetronService;

/**
 * @author lubincan
 * @date 2015年12月25日
 * @todo TODO
 * @modify	<BR>
 * 1.2015年12月25日 lubincan Create file.
 */
public class MetronMessageListener{
	public void handleMessage(String message ) {
		// TODO Auto-generated method stub
	 try {
	   ChatObject msg = new ChatObject();
     msg.setUserName("系统后台探测威胁日志");
     msg.setMessage(message);
	   SocketMetronService.getNettyService().getBroadcastOperations().sendEvent("chatevent", msg);
	   SocketMetronService.getNettyService().getBroadcastOperations().sendEvent("totalLog", new Random().nextInt(1000));
  } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
	 
	}

}
