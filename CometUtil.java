package com.thinkgem.jeesite.common.utils;

import com.thinkgem.jeesite.common.entity.CacheManager;
import com.thinkgem.jeesite.common.entity.Comet;
import com.thinkgem.jeesite.modules.question.utils.Constants;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by SoleGlory on 2017/10/31.
 */
public final class CometUtil{
	public static final String[] contractchannel = {"msgCount", "msgData"};

	public static final byte deleted = 0;

	/**
	 * 发送消息给所有监听channel参数的Engine
	 * @param content
	 */
	public static void sendMsgToJsp(int index, String content){
		CometEngine engine = CometContext.getInstance().getEngine();
		try {
			engine.sendToAll(contractchannel[index], URLEncoder.encode(content.replace(" ", ""),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 推送给所有的客户端
	 * @param comet
	 */
	public static void pushToAll(Comet comet){
		try {
			CometEngine engine = CometContext.getInstance().getEngine();
			//推送到所有客户端
			engine.sendToAll(Constants.CHANNEL_MSGCOUNT,comet.getMsgCount());
			engine.sendToAll(Constants.CHANNEL_MSG_DATA,comet.getMsgData());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

	}

	private static void doCache(final CometConnection conn,String userId) {
		if (userId != null) {
			CacheManager.putContent(conn.getId(), String.valueOf(userId), Constants.EXPIRE_AFTER_ONE_HOUR);
		}
	}

	/**
	 * 推送给指定客户端
	 * @param comet
	 */
	public static void pushTo(Comet comet) {
		try {
			ConnectEvent connEvent = (ConnectEvent) CacheManager.getContent(comet.getUserId()).getValue();
			final CometConnection conn = connEvent.getConn();
			//建立连接和用户的关系
			doCache(conn, comet.getUserId());
			final String connId = conn.getId();
			CometEngine engine = CometContext.getInstance().getEngine();
			if (CacheManager.getContent(connId).isExpired()) {
				doCache(conn, comet.getUserId());
			}
			System.out.println(comet.getUserId());
			//推送到指定的客户端
			engine.sendTo(Constants.CHANNEL_MSGCOUNT, engine.getConnection(connId), comet.getMsgCount());
			engine.sendTo(Constants.CHANNEL_MSG_DATA, engine.getConnection(connId), comet.getMsgData());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
}  