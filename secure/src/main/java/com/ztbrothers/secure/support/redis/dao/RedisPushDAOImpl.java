/**
 * Copyright(c) Yystar Technology Co.,Ltd
 * project：redis
 * package：com.mkfree.redis.test
 * fileName：RedisDAOImpl.java
 * date：2015年12月25日
 */
package com.ztbrothers.secure.support.redis.dao;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * @author lubincan
 * @date 2015年12月25日
 * @todo TODO
 * @modify	<BR>
 * 1.2015年12月25日 lubincan Create file.
 */
public class RedisPushDAOImpl {
private RedisTemplate<String, Object> redisTemplate = null;  
private String channel;  
  public RedisPushDAOImpl() {  
   
  }

	public void sendMessage(String channel, Serializable message) {
		redisTemplate.convertAndSend(channel, message);
		
	}
	
	public void sendMessage(Serializable message) {
		try {
      redisTemplate.convertAndSend(channel, new String(message.toString().getBytes(),"UTF-8"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		
	}

	public RedisTemplate getRedisTemplate() {
		// TODO Auto-generated method stub
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;  
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
 
}
