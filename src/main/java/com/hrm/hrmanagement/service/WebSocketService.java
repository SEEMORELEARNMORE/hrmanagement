package com.hrm.hrmanagement.service;

import com.alibaba.fastjson.JSON;
import com.hrm.hrmanagement.entity.WebSocketClient;
import com.hrm.hrmanagement.entity.vo.Id;
import com.hrm.hrmanagement.entity.vo.TempNoticeVo;
import com.hrm.hrmanagement.mapper.PersonMapper;
import com.hrm.hrmanagement.mapper.TempNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{userId}")
@Component
@Service
public class WebSocketService {
    private static ConcurrentHashMap<String, WebSocketService> webSocketSet = new ConcurrentHashMap<String, WebSocketService>();
    @Autowired
    private static TempNoticeMapper tempNoticeMapper;


    @Autowired
    public void setDevicesDaoMapper(TempNoticeMapper tempNoticeMapper) {
        WebSocketService.tempNoticeMapper = tempNoticeMapper;
    }
    @Autowired
    private static PersonMapper personMapper;

    @Autowired
    public void setPersonMapper(PersonMapper personMapper) {
        WebSocketService.personMapper = personMapper;
    }

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, WebSocketClient> webSocketMap = new ConcurrentHashMap<>();


    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        if(!webSocketMap.containsKey(userId))
        {
            addOnlineCount(); // 在线数 +1
        }
        this.session = session;
        this.userId= userId;
        webSocketSet.put(userId, this);

        log.info("----------------------------------------------------------------------------");
        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
        try {
            List<TempNoticeVo> tn = tempNoticeMapper.selectList(this.userId);
            String json = JSON.toJSONString(tn);
            this.session.getBasicRemote().sendText(json);
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            if(webSocketMap.size()>0)
            {
                //从set中删除
                subOnlineCount();
            }
        }
        log.info("----------------------------------------------------------------------------");
        log.info(userId+"用户退出,当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
//     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String noticeid, Session session) throws IOException {
        log.info("收到用户消息:"+userId+",报文:"+noticeid);
        //可以群发消息
        //消息保存到数据库、redis
//        WebSocketNotice notice= JSON.parseObject(message,WebSocketNotice.class);
//        System.out.println(notice.getNoticeid());
        Integer id =Integer.valueOf(noticeid);
        System.out.println("noticeid的值为："+noticeid);


        List<Id> ids = personMapper.findAllId();
        for(int i=0;i<ids.size();i++) {
            if(ids.get(i).getId().equals(userId))
                ids.remove(i);
        }

        if(id!=null)
            tempNoticeMapper.addList(id,ids);
        else{
            System.out.println("noticeid为空");
        }
        sendMessage(noticeid);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }

    /**
     * 连接服务器成功后主动推送
     */
    public void sendMessage(String message) throws IOException {
        for (String key:webSocketSet.keySet()){
        if(!userId.equals(key)){
            System.out.println("userid为："+key);
        List<TempNoticeVo> tn = tempNoticeMapper.selectList(key);
        String json = JSON.toJSONString(tn);
        webSocketSet.get(key).session.getBasicRemote().sendText(json);
            log.info( "向用户"+key+"主动推送的数据："+json);
    }
}



    }

    /**
     * 向指定客户端发送消息
     * @param userId
     * @param message
     */
    public static void sendMessage(String userId,String message){
//        try {
//            WebSocketClient webSocketClient = webSocketMap.get(userId);
//            if(webSocketClient!=null){
//                webSocketClient.getSession().getBasicRemote().sendText(message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
//        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketService.onlineCount--;
    }

    public static void setOnlineCount(int onlineCount) {
        WebSocketService.onlineCount = onlineCount;
    }


    public static ConcurrentHashMap<String, WebSocketClient> getWebSocketMap() {
        return webSocketMap;
    }

    public static void setWebSocketMap(ConcurrentHashMap<String, WebSocketClient> webSocketMap) {
        WebSocketService.webSocketMap = webSocketMap;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

}
