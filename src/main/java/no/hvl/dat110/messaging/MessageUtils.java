package no.hvl.dat110.messaging;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		byte[] data = message.getData();
		return ByteBuffer.allocate(SEGMENTSIZE).put((byte) data.length).put(data).flip().array();
	}

	public static Message decapsulate(byte[] segment) {
		return new Message(Arrays.copyOfRange(segment, 1, 1 + segment[0]));
	}
	
}
