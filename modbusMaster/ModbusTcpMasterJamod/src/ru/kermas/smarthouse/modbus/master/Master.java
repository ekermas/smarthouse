package ru.kermas.smarthouse.modbus.master;

import java.net.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.procimg.SimpleRegister;

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
			InetAddress addr = InetAddress.getByName("192.168.1.20");
			int port = Modbus.DEFAULT_PORT;

			con = new TCPMasterConnection(addr);
			con.setPort(port);
			con.setTimeout(1000);
			con.connect();

			int i = 0;
			//int i = 16384;
			//int i = 4389;
			//int i = 2049;
			int to =  0;
			for (; i <= to; i++) {
				// testReadInputDiscretes(0, 16);

				//testReadCoils(i, 1);
				
				//testReadMultipleRegisters(i, 1);
				
				//testWriteSingleRegister(i, 1);

				//testReadMultipleRegisters(i, 1);
			}
			
			//testWriteSingleRegister(16384, 1);
			/*testWriteSingleRegister(2049, 1);
			testWriteSingleRegister(0, 1);
			testWriteSingleRegister(1, 1);
			testWriteSingleRegister(4389, 1);
			testWriteSingleRegister(4388, 1);*/
			//testReadInputDiscretes(16384, 1);
			//testReadInputDiscretes(16384, 1);
			testReadMultipleRegisters(16384, 1);
			testReadMultipleRegisters(16394, 1);
			//testWriteSingleRegister(16394, 1);
			//testWriteSingleRegister(16385, 1);
			
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
			System.out.println("\n------------------\n");
			System.out.println("ReadInputDiscretesResponse="
					+ res.getDiscretes().toString());
			for (int i = 0; i < res.getDiscretes().size(); i++) {
				System.out.println("ReadInputDiscretesResponse[" + i + "]="
						+ res.getDiscreteStatus(i));
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}

	private void testReadCoils(int ref, int count) {
		try {
			System.out.println("" + ref);
			ReadCoilsRequest req = new ReadCoilsRequest(ref, count);
			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			trans.execute();
			ReadCoilsResponse res = (ReadCoilsResponse) trans.getResponse();
			System.out.println("\n------------------\n");
			System.out
					.println("ReadCoilsResponse=" + res.getCoils().toString());
			for (int i = 0; i < res.getCoils().size(); i++) {
				System.out.println("ReadCoilsResponse[" + i + "]="
						+ res.getCoilStatus(i));
			}
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}

	private void testReadMultipleRegisters(int ref, int count) {
		try {
			ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(
					ref, count);
			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			trans.execute();
			ReadMultipleRegistersResponse res = (ReadMultipleRegistersResponse) trans
					.getResponse();
			System.out.println("\n------------------\n");
			System.out.println("" + ref);
			// System.out.println("ReadMultipleRegistersResponse="
			// + res.getRegisters().length);
			for (int i = 0; i < res.getRegisters().length; i++) {
				System.out.println("ReadMultipleRegistersResponse[" + i + "]="
						+ res.getRegister(i).toUnsignedShort());
				String file_string = toBinary(res.getRegister(i).toBytes());
				System.out.println("ReadMultipleRegistersResponse[" + i + "]="
						+ file_string);
			}
		} catch (ModbusException mioex) {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void testWriteSingleRegister(int ref, int count) {
		try {
			Register reg = new SimpleRegister(count);
			WriteSingleRegisterRequest req = new WriteSingleRegisterRequest(
					ref, reg);
			ModbusTCPTransaction trans = new ModbusTCPTransaction(con);
			trans.setRequest(req);
			trans.execute();
			WriteSingleRegisterResponse res = (WriteSingleRegisterResponse) trans
					.getResponse();
			System.out.println("\n------------------\n");
			System.out.println("" + ref);
				System.out.println("testWriteSingleRegister="
						+ res.getRegisterValue());
		} catch (ModbusException mioex) {
			mioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}
}
