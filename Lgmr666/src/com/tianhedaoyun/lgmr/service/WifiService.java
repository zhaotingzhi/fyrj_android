package com.tianhedaoyun.lgmr.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianhedaoyun.lgmr.LN100.LN100Util;
import com.tianhedaoyun.lgmr.activity.ModelMain;
import com.tianhedaoyun.lgmr.util.Const;
import com.tianhedaoyun.lgmr.util.WifiUtil;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;

public class WifiService extends Service {

	private Intent intent = new Intent("com.tianhedaoyun.lgmr.RECEIVER");
	private ModelMain activity;
	WifiUtil _mwifi;

	/**
	 * 监听wifi
	 */
	public void startListen() {
		Const.service.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (!isWifiConnect()) {
						if (LN100Util.m_client != null) {
							LN100Util.m_client = null;
						}
						intent.putExtra("wifi_status", 2);
						sendBroadcast(intent);
					} else {
						if (!_mwifi.getSSID().contains("LN-100")) {
							intent.putExtra("wifi_status", 2);
							sendBroadcast(intent);
						} else {
							intent.putExtra("wifi_status", 3);
							sendBroadcast(intent);

						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});
	}

	private boolean isWifiConnect() {
		// 如果wifi已经连接上，更换图片显示
		if (_mwifi.checkState() == WifiManager.WIFI_STATE_ENABLED) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startListen();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 返回一个Binder对象
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return new MsgBinder();
	}

	public class MsgBinder extends Binder {
		/**
		 * 获取当前Service的实例
		 * 
		 * @return
		 */
		public WifiService getService() {
			return WifiService.this;
		}
	}
	// 初始化MainActivity对象

	public void setMainActivity(WifiUtil wifiutil) {
		this._mwifi = wifiutil;
	}

}
