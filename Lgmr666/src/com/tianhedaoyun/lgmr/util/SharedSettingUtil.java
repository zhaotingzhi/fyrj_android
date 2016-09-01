package com.tianhedaoyun.lgmr.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.apache.commons.codec.binary.Base64;

import com.tianhedaoyun.lgmr.bean.SettingData;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedSettingUtil {

	private static SettingData data;

	public static void saveObject(Context context, String str, SettingData data) {

		SharedPreferences preferences = context.getSharedPreferences(str, context.MODE_PRIVATE);

		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(data);

			// 将字节流编码成base64的字符串
			String objectBase64 = new String(Base64.encodeBase64(baos.toByteArray()));

			Editor editor = preferences.edit();
			editor.putString("station_data", objectBase64);
			editor.commit();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static SettingData readObject(Context context, String str) {
		SharedPreferences preferences = context.getSharedPreferences(str, context.MODE_PRIVATE);

		String objectBase64 = preferences.getString("station_data", "");
		if (objectBase64 == "") {
			return null;
		}

		// 读取字节
		byte[] base64 = Base64.decodeBase64(objectBase64.getBytes());

		// 封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);

		try {
			// 再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);

			// 读取对象
			data = (SettingData) bis.readObject();
			return data;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean delSettingData(Context context, String str) {
		SharedPreferences preferences = context.getSharedPreferences(str, context.MODE_PRIVATE);

		return preferences.edit().clear().commit();

	}

}
