package com.thinkgem.jeesite.common.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by SoleGlory on 2017/10/31.
 */
public class Comet {
    private String userId;
    private String msgCount;
    private List<Map> msgData;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMsgCount() {
        return msgCount;
    }
    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }
    public List<Map> getMsgData() {
        return msgData;
    }
    public void setMsgData(List<Map> msgData) {
        this.msgData = msgData;
    }
}
