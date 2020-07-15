package bekl;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import java.lang.Math;
import java.lang.Thread;
import java.lang.Exception;
import java.lang.InterruptedException;

import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;


@ServerEndpoint(value = "/index")
public class WebSocket {

	 /**
     * 连接建立后触发的方法
     */
    @OnOpen
    public void onOpen(final Session session) throws IOException {
        System.out.println("onOpen");
    }

    /**
     * 连接关闭后触发的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("onClose");
    }

    /**
     * 接收到客户端消息时触发的方法
     */
    @OnMessage
    public void onMessage(final String params, final Session session) throws Exception {
        System.out.println("OnMessage");

        while(true) {
        	String data = (String)MyServletContext.arrayBlockingQueue1.take();

        	session.getBasicRemote().sendText(data);
        	
        	Thread.sleep(1000);	     	
        }
    }

    /**
     * 发生错误时触发的方法
     */
    @OnError
    public void onError(final Session session, final Throwable error) {
        System.out.println("onError");
    }
    
}
