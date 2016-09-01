package com.tianhedaoyun.lgmr.LN100;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import com.tianhedaoyun.lgmr.util.StringUtils;
import com.tianhedaoyun.lgmr.util.WifiUtil;

import android.content.Context;

public class LN100Util {

	public static final String CLIENT_SERVER_IP = "192.168.0.255";
	public static final int TCP_CLIENT_PORT = 65521;
	public static final int UDP_CLIENT_PORT = 65520;
	public static final int UDP_SERVER_PORT = 65519;
	private static WifiUtil m_wifi;
	private static String localAddress;

	private static String serverIp = "";
	public static Socket m_client;
	private static LN100Util _instance;

	public static LN100Util getInstance() {
		if (LN100Util._instance == null) {
			LN100Util._instance = new LN100Util();
		}
		return LN100Util._instance;
	}

	private static Udprecv udprecv = null;

	private static byte[] getConnSendMsg(String localAddress) {
		String str = StringUtils.encode("@IPBCC," + localAddress + ",");
		String sum = StringUtils.makeChecksum(str);
		String hexSum = StringUtils.encode(sum);
		String sendMsg = str + hexSum + StringUtils.HexCR;
		byte[] data = StringUtils.hexStr2Bytes(sendMsg);
		return data;
	}

	private class Tcpconn implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(50);
				if (serverIp != "") {
					if (udprecv.isAlive()) {
						udprecv.interrupt();
					}
				}
				m_client = new Socket(serverIp, TCP_CLIENT_PORT);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// 接收对方udp数据包
	private class Udprecv extends Thread {
		DatagramSocket udpSocket;
		byte[] data = new byte[256];
		DatagramPacket udpPacket;

		@Override
		public void run() {

			byte[] data = new byte[256];
			try {
				udpSocket = new DatagramSocket(UDP_CLIENT_PORT);
				udpPacket = new DatagramPacket(data, data.length);
			} catch (SocketException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					udpSocket.receive(udpPacket);
				} catch (Exception e) {
				}
				if(udpPacket==null||udpPacket.getLength()==0){
					continue;
				}
				final String codeString = new String(data, 0, udpPacket.getLength());
				String[] strs = codeString.split(",");
				serverIp = strs[1];
			}
		}
	}

	// udp广播
	public class BroadCastUdp extends Thread {
		private byte[] dataString;
		private DatagramSocket udpSocket;
		private byte[] buffer = new byte[256];

		public BroadCastUdp(byte[] dataString) {
			this.dataString = dataString;
		}

		public void run() {
			DatagramPacket dataPacket = null;

			try {
				udpSocket = new DatagramSocket(UDP_SERVER_PORT);

				dataPacket = new DatagramPacket(buffer, 256);
				dataPacket.setData(dataString);
				dataPacket.setLength(dataString.length);
				dataPacket.setPort(UDP_SERVER_PORT);

				InetAddress broadcastAddr;

				broadcastAddr = InetAddress.getByName(CLIENT_SERVER_IP);
				dataPacket.setAddress(broadcastAddr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				udpSocket.send(dataPacket);
				sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}

			udpSocket.close();
		}
	}

	public static void connect(Context context) {
		m_wifi = new WifiUtil(context);
		localAddress = m_wifi.getIPAddress();
		LN100Util.getInstance().new BroadCastUdp(getConnSendMsg(localAddress)).start();
		udprecv = LN100Util.getInstance().new Udprecv();
		udprecv.start();
		new Thread(LN100Util.getInstance().new Tcpconn()).start();

	}

	public Boolean isConnected() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (m_client == null) {
			return false;
		} else {
			return true;
		}
	}

	public static byte[] sendMessage(final String s) {
		final String encode = StringUtils.encode(s);
		final String hexChecksum = StringUtils.encode(StringUtils.makeChecksum(encode));
		final String string = String.valueOf(encode) + hexChecksum + StringUtils.HexCR;
		byte[] bytes = StringUtils.hexStr2Bytes(string);
		return bytes;
	}

	public boolean sendMessageHandle(final byte[] array) {
		if (LN100Util.m_client == null) {
			return false;
		}
		try {
			LN100Util.m_client.getOutputStream().write(array);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public String hexChecksum(String s) {
		return StringUtils.encode(StringUtils.makeChecksum(s));
	}

	public Socket getM_client() {
		return m_client;
	}
}
