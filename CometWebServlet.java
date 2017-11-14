package com.thinkgem.jeesite.common.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.thinkgem.jeesite.common.entity.CacheManager;
import com.thinkgem.jeesite.common.utils.CometUtil;
import com.thinkgem.jeesite.modules.question.utils.Constants;
import org.comet4j.core.CometConnection;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

/**
 * Created by SoleGlory on 2017/11/1.
 */
public class CometWebServlet extends ConnectListener implements ServletContextListener{
    /**
     * 初始化上下文
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // CometContext ： Comet4J上下文，负责初始化配置、引擎对象、连接器对象、消息缓存等。
        CometContext cc = CometContext.getInstance();
        // 注册频道，即标识哪些字段可用当成频道，用来作为向前台传送数据的“通道”
        for (String channel : CometUtil.contractchannel){
            cc.registChannel(channel);//注册应用的channel
        }
        //添加监听器
        CometEngine engine = CometContext.getInstance().getEngine();
        engine.addConnectListener(this);
//        CometUtil.setCometEngine(engine);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean handleEvent(ConnectEvent connEvent){
        // TODO Auto-generated method stub
        final CometConnection conn = connEvent.getConn();
        Object userId = conn.getRequest().getSession().getAttribute("currentUserId");
        CacheManager.putContent(userId.toString(), connEvent);
        return true;
    }
}
