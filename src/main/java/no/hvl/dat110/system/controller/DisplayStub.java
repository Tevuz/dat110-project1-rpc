package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public void write (String message) {

		// marshall parameter to write call (string parameter)
		byte[] request = RPCUtils.marshallString(message);

		// make remote procedure call for write
		byte[] response = rpcclient.call((byte)Common.WRITE_RPCID, request);

		// unmarshall the return value from the call (void parameter)
		RPCUtils.unmarshallVoid(response);
	}
}
