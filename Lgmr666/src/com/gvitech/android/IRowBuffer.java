package com.gvitech.android;

public class IRowBuffer {
	protected long _piObject = 0;
	
	private native int getFieldCountT(long rowbuffer);
	public int GetFieldCount(){
		return this.getFieldCountT(this._piObject);
	}

	private native int getFieldsT(long rowbuffer);
	public IFieldInfoCollection GetFields(){
		IFieldInfoCollection fieldInfoCollection = new IFieldInfoCollection();
		fieldInfoCollection._piObject = this.getFieldsT(this._piObject);
		return fieldInfoCollection;
	}
	
	private native int fieldIndexT(long rowbuffer, String newVal);
	public int FieldIndex(String newVal){
		return this.fieldIndexT(this._piObject, newVal);
	}
	
	private native byte getInt8T(long rowbuffer, int pos);
	public byte GetInt8(int pos){
		return this.getInt8T(this._piObject, pos);
	}
	
	private native short getInt16T(long rowbuffer, int pos);
	public short GetInt16(int pos){
		return this.getInt16T(this._piObject, pos);
	}
	
	private native int getInt32T(long rowbuffer, int pos);
	public int GetInt32(int pos){
		return this.getInt32T(this._piObject, pos);
	}
	
	private native long getInt64T(long rowbuffer, int pos);
	public long GetInt64(int pos){
		return this.getInt64T(this._piObject, pos);
	}
	
	private native String getStringT(long rowbuffer, int pos);
	public String GetString(int pos){
		return this.getStringT(this._piObject, pos);
	}
	
	private native float getFloatT(long rowbuffer, int pos);
	public float GetFloat(int pos){
		return this.getFloatT(this._piObject, pos);
	}
	
	private native double getDoubleT(long rowbuffer, int pos);
	public double GetDouble(int pos){
		return this.getDoubleT(this._piObject, pos);
	}
	
	private native String getDateTimeT(long rowbuffer, int pos);
	public String GetDateTime(int pos){
		return this.getDateTimeT(this._piObject, pos);
	}
	
	private native String getBlobT(long rowbuffer, int pos);
	public String GetBlob(int pos){
		return this.getBlobT(this._piObject, pos);
	}
	
	private native String getGeometryT(long rowbuffer, int pos);
	public String GetGeometry(int pos){
		return this.getGeometryT(this._piObject, pos);
	}
	public long get_piObject(){
		return _piObject;
	}
	
}
