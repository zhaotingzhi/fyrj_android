package com.gvitech.android;

public class ICodedValueDomain extends IDomain{
	public ICodedValueDomain(long id) {
		super(id);
	}
	
	private native int getCodeCountT(long domain);
	public int GetCodeCount(){
		return this.getCodeCountT(this._piObject);
	}
	
	private native String getCodeNameT(long domain, int pos);
	public String getCodeName(int pos){
		return getCodeNameT(this._piObject, pos);
	}
	
	private native byte getInt8T(long domain, int pos);
	public byte GetInt8(int pos){
		return this.getInt8T(this._piObject, pos);
	}
	
	private native short getInt16T(long domain, int pos);
	public short GetInt16(int pos){
		return this.getInt16T(this._piObject, pos);
	}
	
	private native int getInt32T(long domain, int pos);
	public int GetInt32(int pos){
		return this.getInt32T(this._piObject, pos);
	}
	
	private native long getInt64T(long domain, int pos);
	public long GetInt64(int pos){
		return this.getInt64T(this._piObject, pos);
	}
	
	private native String getStringT(long domain, int pos);
	public String GetString(int pos){
		return this.getStringT(this._piObject, pos);
	}
	
	private native float getFloatT(long domain, int pos);
	public float GetFloat(int pos){
		return this.getFloatT(this._piObject, pos);
	}
	
	private native double getDoubleT(long domain, int pos);
	public double GetDouble(int pos){
		return this.getDoubleT(this._piObject, pos);
	}
	
	private native String getDateTimeT(long domain, int pos);
	public String GetDateTime(int pos){
		return this.getDateTimeT(this._piObject, pos);
	}
}
