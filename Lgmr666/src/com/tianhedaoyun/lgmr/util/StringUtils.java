package com.tianhedaoyun.lgmr.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class StringUtils {
	//回车
	public static String HexCR;
	//换行
	public static String HexLF;
	private static String hexString;

	static {
		StringUtils.HexCR = "0D";
		StringUtils.HexLF = "0A";
		StringUtils.hexString = "0123456789ABCDEF";
	}

	//字节数组转16进制字符串
	public static String Bytes2HexString(final byte[] array) {
		String string = "";
		for (int i = 0; i < array.length; ++i) {
			String s = Integer.toHexString(0xFF & array[i]);
			if (s.length() == 1) {
				s = String.valueOf('0') + s;
			}
			string = String.valueOf(string) + s.toUpperCase();
		}
		return string;
	}
	
	//字符串解码
	public static String decode(final String s) {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(s.length() / 2);
		for (int i = 0; i < s.length(); i += 2) {
			byteArrayOutputStream.write(
					StringUtils.hexString.indexOf(s.charAt(i)) << 4 | StringUtils.hexString.indexOf(s.charAt(i + 1)));
		}
		return new String(byteArrayOutputStream.toByteArray());
	}

	//字符串编码成16进制
	public static String encode(final String s) {
		final byte[] bytes = s.getBytes();
		final StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (int i = 0; i < bytes.length; ++i) {
			sb.append(StringUtils.hexString.charAt((0xF0 & bytes[i]) >> 4));
			sb.append(StringUtils.hexString.charAt((0xF & bytes[i]) >> 0));
		}
		return sb.toString();
	}

	//16进制转换成字节数组
	public static byte[] hexStr2Bytes(final String s) {
		final int n = s.length() / 2;
		System.out.println(n);
		final byte[] array = new byte[n];
		for (int i = 0; i < n; ++i) {
			final int n2 = 1 + i * 2;
			array[i] = (byte) (0xFF & Integer.decode("0x" + s.substring(i * 2, n2) + s.substring(n2, n2 + 1)));
		}
		return array;
	}
	
	
	//16进制字符串转换成普通字符串
	public static String hexStr2Str(final String s) {
		final char[] charArray = s.toCharArray();
		final byte[] array = new byte[s.length() / 2];
		for (int i = 0; i < array.length; ++i) {
			array[i] = (byte) (0xFF & 16 * "0123456789ABCDEF".indexOf(charArray[i * 2])
					+ "0123456789ABCDEF".indexOf(charArray[1 + i * 2]));
		}
		return new String(array);
	}

	//转换成16进制字符串
	public static String toHexString(final String s) {
		String string = "";
		for (int i = 0; i < s.length(); ++i) {
			string = String.valueOf(string) + Integer.toHexString(s.charAt(i));
		}
		return string;
	}

	public static String toStringHex(final String s) {
		final byte[] array = new byte[s.length() / 2];
		int n = 0;
		while (true) {
			Label_0032: {
				if (n < array.length) {
					break Label_0032;
				}
				try {
					return new String(array, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final int n2 = n * 2;
				final int n3 = 2 + n * 2;
				try {
					array[n] = (byte) (0xFF & Integer.parseInt(s.substring(n2, n3), 16));
					++n;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	//核对校验码
	public static boolean checkChecksum(final String s, final String s2) {
        return s.length() != 0 && s2.length() != 0 && makeChecksum(s).equals(s2);
    }
	
	//生成校验码
	public static String makeChecksum(final String s) {
		if (s.length() == 0) {
			return "";
		}
		int n = 0;
		for (int length = s.length(), i = 0; i < length; i += 2) {
			n += Integer.parseInt(s.substring(i, i + 2), 16);
		}
		String s2 = Integer.toHexString(n % 256);
		if (s2.length() < 2) {
			s2 = "0" + s2;
		}
		return s2.toUpperCase();
	}
	
	/** 
     * 给参数返回指定小数点后几位的四舍五入 
     * @param sourceData    传入的要舍取的元数据 
     * @param str 取舍的格式（主要用到"#.0"的格式，此为小数点后1位；"#.00"为小数点后2位，以此类推） 
     * @return 舍取后的 数据 
     */  
    public static double getDouble(double sourceData,String sf)  
    {  
        DecimalFormat df = new DecimalFormat(sf);  
        String str = df.format(sourceData);  
        return Double.parseDouble(str);  
    }  
      
    /** 
     * 给参数返回指定小数点后 a 位的四舍五入 
     * @param sourceData 要取舍的原数据 
     * @param a 小数点 后的 位数（如：10：小数点后1位；100：小数据后2位以此类推） 
     * @return 舍取后的 数据 
     */  
    public static float getFloatRound(double sourceData,int a)  
    {  
        int i = (int) Math.round(sourceData*a);     //小数点后 a 位前移，并四舍五入  
        float f2 = (float) (i/(float)a);        //还原小数点后 a 位  
        return f2;  
    }

}
