package bekl;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class MyServletContext implements ServletContextListener {
	
	//声明 静态队列块
    public static ArrayBlockingQueue arrayBlockingQueue1;
    
    //初始化静态队列块的大小
    static {
        arrayBlockingQueue1 = new ArrayBlockingQueue(128);
    }
    
    //context初始化
    public void contextInitialized(ServletContextEvent sce) {
        
        System.out.println("------ServletContextListener contextInitialized ---------");

        //初始化时 开启接收线程  接收外界发送过来的数据
        ReceveThread receveThread = new ReceveThread();
        //启动接收线程
        receveThread.start();

    }
    
    //context 销毁时执行的函数
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("------ServletContextListener contextDestroyed -----------");
    }
 
        
    private static class ReceveThread extends Thread {
    	
    	public static void reseve() throws IOException{ 
    		System.out.println("enter reseve");
    		//定义一个1k的缓冲区
            byte[] buf = new byte[1024];  
            
            //1.创建客户端Socket，指定服务器地址和端口
            Socket socket=new Socket("192.168.1.254", 10050);
            
         	// 2.通过Scoket,获取输出流对象
            OutputStream os = socket.getOutputStream();

            // 3.写出数据.
            os.write("start_log".getBytes());
            
            // 3.1 获取输入流,读取文件数据
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());

            // 4. 读写数据
            int len;
            while ((len = bis.read(buf)) != -1) {
            	String Info = new String(buf); 
            	System.out.println(Info);
            	
            	try {
            		arrayBlockingQueue1.add(Info);
				} catch (Exception e) {
					System.out.println("arrryBlock full");
				}
            	
            } 

            socket.close();
    		System.out.println("socket close");   		
        } 
    	
    	
        @Override
        public void run() {
        	try {
				reseve();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }
      
}