package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RPCUtils {

	// Encapsulate the rpcid and payload in a byte array according to the RPC message syntax / format
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		return ByteBuffer.allocate(payload.length + 1).put(rpcid).put(payload).flip().array();
	}

	// Decapsulate the rpcid and payload in a byte array according to the RPC message syntax
	public static byte[] decapsulate(byte[] rpcmsg) {
		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		return str.getBytes(StandardCharsets.UTF_16LE);
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		return StandardCharsets.UTF_16LE.decode(ByteBuffer.wrap(data)).toString();
	}
	
	public static byte[] marshallVoid() {
		return new byte[1];
	}

	public static void unmarshallVoid(byte[] data) {
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {
		return new byte[]{ b ? (byte) 1 : (byte) 0};
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		return (data[0] > 0);
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		return ByteBuffer.allocate(4).putInt(x).array();
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer.wrap(data).getInt();
	}
}
