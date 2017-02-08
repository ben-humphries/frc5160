package frc.team5160.rpiviz;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(byte[].class);
		kryo.register(ChatMessage.class);
	}

	static public class ChatMessage {
		public int x,y; 
		public byte[] text;
	}
}
