package com.tianhedaoyun.lgmr.activity;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class tool extends BaseActivity {
	public static boolean IsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	@SuppressWarnings("static-access")
	public static Bitmap scaleBitmap(Bitmap bt, float scale) {
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		bt = bt.createBitmap(bt, 0, 0, bt.getWidth(), bt.getHeight(), matrix, true);
		return bt;
	}

}
