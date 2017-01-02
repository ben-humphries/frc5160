package frc.team5160.rpiviz;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;

import com.esotericsoftware.kryonet.Server;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * This will be what runs on the raspberry pi/android phone/roborio
 * It will create a kryonet server to stream compressed opencv Mats
 * The kryonet is a custom bundle that is patched for android
 */
public class MJPGServer {
	Server server;
	public MJPGServer(){
		
		server = new Server();
		Network.register(server);
		server.start();
		try {
			server.bind( 8085,8086);
		} catch (IOException e) {
			try {
				server.bind( 8087,8088);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public MJPGServer(NetworkTable networktable){
		try {
			networktable.putString("kryoserverip", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		server = new Server();
		Network.register(server);
		server.start();
		try {
			server.bind( 8085,8086);
		} catch (IOException e) {
			try {
				server.bind( 8087,8088);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	public void postMat(Mat mat){
		ByteArrayOutputStream k = new ByteArrayOutputStream();
		try {
			BufferedImage image = ImageOps.toBufferedImage(mat);
			ImageIO.write(image, "jpg", k);
			Network.ChatMessage c = new Network.ChatMessage();
	 	  	c.x = image.getWidth();
	 	  	c.y = image.getHeight();
	 	  	c.text = k.toByteArray();
	 	  	server.sendToAllUDP(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
