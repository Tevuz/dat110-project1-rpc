package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.MessagingServer;

import java.util.HashMap;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;
	
	// hashmap to register RPC methods which are required to extend RPCRemoteImpl
	// the key in the hashmap is the RPC identifier of the method
	private HashMap<Byte,RPCRemoteImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte,RPCRemoteImpl>();
		
	}
	
	public void run() {
		
		// the stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPCIDSTOP, this);
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
			
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
			// - receive a Message containing an RPC request
			Message requestMsg = connection.receive();
			// - extract the identifier for the RPC method to be invoked from the RPC request
			byte rpcid = requestMsg.getData()[0];
			byte[] request = RPCUtils.decapsulate(requestMsg.getData());

			// stop the server if it was stop methods that was called
			if (rpcid == RPCCommon.RPCIDSTOP) {
				stop = true;
			}

			// - lookup the method to be invoked
			RPCRemoteImpl service = services.get(rpcid);

			// - invoke the method
			byte[] reply = service.invoke(request);

			// - send back the message containing RPC reply
			Message replyMsg = new Message(RPCUtils.encapsulate(rpcid, reply));
			connection.send(replyMsg);
		}
	}
	
	// used by server side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {

		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}
		
		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
		
	}
}
