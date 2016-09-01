package com.gvitech.android;

import com.gvitech.android.EnumValue.gviFieldType;
import com.gvitech.android.EnumValue.gviDomainType;

public class IDomain {
	protected long _piObject = 0;
	
	public IDomain(long id){
		_piObject = id;
	}

	private native int getFieldTypeT(long domain);
	public gviFieldType getFieldType() {
		int n = this.getFieldTypeT(this._piObject);
		gviFieldType r = gviFieldType.gviFieldUnknown;
		for(gviFieldType e : gviFieldType.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	private native String getNameT(long domain);
	public String getName(){
		return getNameT(this._piObject);
	}
	
	private native int getDomainTypeT(long domain);
	public gviDomainType getDomainType() {
		int n = this.getDomainTypeT(this._piObject);
		gviDomainType r = gviDomainType.gviDomainRange;
		for(gviDomainType e : gviDomainType.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	public long get_piObject(){
		return _piObject;
	}
	
}
