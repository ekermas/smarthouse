package ru.kermas.smarthouse.modbus.master;

import java.net.*;
import java.io.*;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.ip.IpParameters;

public class Master2 {

	public static void main(String[] args) {
		try {

			ModbusFactory factory = new ModbusFactory();
			IpParameters params = new IpParameters();
			params.setHost("192.168.0.11");
			params.setPort(502);
			params.setEncapsulated(true);
			ModbusMaster master = factory.createTcpMaster(params, false);
			// master.setRetries(4);
			master.setTimeout(2000);
			master.setRetries(0);

			long start = System.currentTimeMillis();
			try {
				master.init();
				for (int i = 0; i < 100; i++) {
					System.out.println(master.getValue(1,
							RegisterRange.COIL_STATUS, 100+1,
							DataType.BINARY));
				}
			} finally {
				master.destroy();
			}

			System.out.println("Took: " + (System.currentTimeMillis() - start)
					+ "ms");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// main

}
