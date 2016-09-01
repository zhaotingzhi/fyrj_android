package com.gvitech.android;

import com.gvitech.android.EnumValue.gviFieldType;

public class IFieldInfo {
	protected long _piObject = 0;

	private native int getFieldTypeT(long fieldInfo);
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
	
	private native String getNameT(long fieldInfo);
	public String getName(){
		return getNameT(this._piObject);
	}
	
	private native int getDomainT(long fieldInfo);
	public IDomain GetDomain(){		
		int domainAddr = this.getDomainT(this._piObject);	
		IDomain domain = new IDomain(domainAddr);		
		return domain;
	}
}
