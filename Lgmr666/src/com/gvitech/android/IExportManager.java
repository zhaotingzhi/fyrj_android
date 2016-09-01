package com.gvitech.android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;

public class IExportManager {
	
	private Context context;
	private native int[] exportImageT(String newVal);	
	
	public Bitmap exportImage(){		
		Bitmap bmpRet = null;
		String strCacheDir = context.getExternalCacheDir().getAbsolutePath();
		String strFile = strCacheDir + "/Screenshot.raw";
		int[] ai = this.exportImageT(strFile);
		int isOK = ai[0];
		int width = ai[1];
		int height = ai[2];
		if(isOK == 1){
			try {
				FileInputStream fileStream = new FileInputStream(strFile);
				int fileLen = fileStream.available();
				byte[] fileContent = new byte[fileLen];
				fileStream.read(fileContent);
				fileStream.close();
				
				ByteBuffer buffer = ByteBuffer.wrap(fileContent);
				bmpRet = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				bmpRet.copyPixelsFromBuffer(buffer);
				
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}		
		return bmpRet;
	}
	
	public void setContext(Context ctx) {
		this.context = ctx;
	}
}
