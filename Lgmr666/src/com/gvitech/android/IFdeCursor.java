package com.gvitech.android;

public class IFdeCursor {
	protected long _piObject = 0;
	
	private native int nextRowT(long fdecursor);
	public IRowBuffer NextRow(){
		IRowBuffer buffer = new IRowBuffer();
		buffer._piObject = this.nextRowT(this._piObject);
		return buffer;
	}

}
