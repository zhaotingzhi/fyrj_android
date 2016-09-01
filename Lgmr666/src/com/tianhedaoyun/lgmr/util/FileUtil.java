package com.tianhedaoyun.lgmr.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;

public class FileUtil {

	public static void saveTxt(String str, Context context) {
		// 添加文件写入和创建的权限

		String aaa = context.getExternalFilesDir("").getAbsolutePath() + File.separator + "aaa.txt";
		File file = new File(aaa);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter pw = new FileWriter(file, true);
			pw.write(str + "\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveCsv(String str, Context context,String mPath,String mName) {
		// 添加文件写入和创建的权限
		String name = mPath + File.separator + mName+".csv";
		File file = new File(name);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter pw = new FileWriter(file, true);
			pw.write(str + "\n");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void delCsv(Context context) {
		// 添加文件写入和创建的权限
		String aaa = context.getExternalFilesDir("").getAbsolutePath() + File.separator + "bbb.csv";
		File file = new File(aaa);
		if (file.exists()) {
			file.delete();
		}
	}

}
