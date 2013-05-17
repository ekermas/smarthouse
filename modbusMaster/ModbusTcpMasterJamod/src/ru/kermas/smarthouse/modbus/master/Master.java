package ru.kermas.smarthouse.modbus.master;

import java.net.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;

public class Master {

	TCPMasterConnection con = null; // the connection

	public static void main(String[] args) {
		try {
			Master master = new Master();
			master.test();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// main

	public void test() {
		try {
			InetAddress addr = InetAddress.getByName("192.168.1.46");
			int port = Modbus.DEFAULT_PORT;

			con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.setTimeout(5000);
			con.connect();

			testReadInputDiscretes(0, 0);

			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void testReadInputDiscretes(int ref, int count) {
		try {
			ReadInputDiscretesRequest req = new ReadInputDiscretesRequest(ref,
					count);
			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			trans.execute();
			ReadInputDiscretesResponse res = (ReadInputDiscretesResponse) trans
					.getResponse();
			System.out.println("Digital Inputs Status="
					+ res.getHexMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
