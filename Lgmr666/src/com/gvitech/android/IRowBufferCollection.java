package com.gvitech.android;

public class IRowBufferCollection {
	protected long _piObject = 0;

	private native int getCountT(long buffercollection);
	public int GetCount(){
		return this.getCountT(this._piObject);
	}

	private native int getRowBufferT(long buffercollection, int index);
	public IRowBuffer GetRowBuffer(int index){
		IRowBuffer rowbuffer = new IRowBuffer();
		rowbuffer._piObject = this.getRowBufferT(this._piObject, index);
		return rowbuffer;
	}
	
}
