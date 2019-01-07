//package MyChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//发送端
class Send implements Runnable{
	private DatagramSocket ds;
	public Send(DatagramSocket ds) {
		this.ds = ds;
	}
	public void run() {
		try {
			while(true) {
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ds.receive(dp);
				String ip = dp.getAddress().getHostAddress();
				String data = new String(dp.getData(), 0, dp.getLength());
				System.out.println(ip+"::"+data);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("接受失败。。。");
		}
	}
}

//接收端
class Rece implements Runnable{
	private DatagramSocket ds;
	public Rece(DatagramSocket ds) {
		this.ds = ds;
	}
	public void run() {
		try {
			BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			while((line = bufr.readLine())!=null) {
				if("886".equals(line))
					break;
				byte[] buf = line.getBytes();
				DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.1.100"), 10002);
				ds.send(dp);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("发送失败。。。。");
		}
	}
}		

public class MyChatDemo {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		DatagramSocket sendSocket = new DatagramSocket();
		new Thread(new Send(sendSocket)).start();
		
		DatagramSocket receSocket = new DatagramSocket();
		new Thread(new Rece(receSocket)).start();
	}

}
