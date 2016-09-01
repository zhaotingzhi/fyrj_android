package com.gvitech.android;

public class IFieldInfoCollection {
	protected long _piObject = 0;

	private native int getCountT(long fieldcollection);
	public int GetCount(){
		return this.getCountT(this._piObject);
	}

	private native int getFieldInfoT(long fieldcollection, int index);
	public IFieldInfo GetFieldInfo(int index){
		IFieldInfo fieldInfo = new IFieldInfo();
		fieldInfo._piObject = this.getFieldInfoT(this._piObject, index);
		return fieldInfo;
	}
	
	private native int indexOfT(long fieldcollection, String newVal);
	public int IndexOf(String newVal){
		return this.indexOfT(this._piObject, newVal);
	}
}
