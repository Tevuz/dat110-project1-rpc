package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {

		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		RPCClient displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		RPCClient sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

		DisplayStub display = new DisplayStub(displayclient);
		SensorStub sensor = new SensorStub(sensorclient);

		displayclient.connect();
		sensorclient.connect();

		for (int i = 0; i < N; i++) {
			int temp = sensor.read();
			display.write("" + temp);
		}
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
