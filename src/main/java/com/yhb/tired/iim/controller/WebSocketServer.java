package com.yhb.tired.iim.controller;

import com.yhb.tired.common.utils.CommonUtil;
import com.yhb.tired.iim.dao.ChatHistoryMapper;
import com.yhb.tired.iim.pojo.ChatHistory;
import com.yhb.tired.iim.service.ChatHistoryService;
import com.yhb.tired.sys.controller.BaseController;
import com.yhb.tired.sys.pojo.User;
import com.yhb.tired.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 09:36
 * @Description:
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer extends BaseController {
    /**
     * 解决websocket无法注入bean的问题
     */
    private static WebSocketServer webSocketServer;

    @Autowired
    public void setWebSocketServer(WebSocketServer webSocketServer){
        WebSocketServer.webSocketServer = webSocketServer;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    @Autowired
    private ChatHistoryService chatHistoryService;
    @Autowired
    private UserService userService;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();//增加一个在线人数
        logger.info("有新连接加入，当前在线人数为:" + getOnlineCount());
        try {
            sendMessage("已成功连接至服务端");
        } catch (IOException e) {
            logger.error("连接发生错误 websocket IO异常");
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        String[] msg_s = message.split("_msg_");
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setId(CommonUtil.getUUID());
        chatHistory.setSender(msg_s[0]);
        chatHistory.setReceiver(msg_s[1]);
        chatHistory.setMsg(msg_s[2]);
        chatHistory.setType(msg_s[4]);
        chatHistory.setStatus("0");
        chatHistory.setCreateDate(new Date());
        int i = webSocketServer.chatHistoryService.insert(chatHistory);
        if (i==0){
            throw new RuntimeException("插入聊天记录失败");
        }
        User user = webSocketServer.userService.getUserById(msg_s[1]);
        for (WebSocketServer item : webSocketSet) {
            System.out.println(item.session.getUserPrincipal().toString());
            if (item.session.getUserPrincipal().toString().equals(user.getUsername())){
                try {
                    item.sendMessage(message + new Date().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * websocket发送错误
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error(">>>>>>>>>>>>>>>websocket发送错误");
        throwable.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public void sendInfo(String message) throws IOException {
        logger.info(message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized int addOnlineCount() {
        return onlineCount++;
    }

    public static synchronized int subOnlineCount() {
        return onlineCount--;
    }
}
