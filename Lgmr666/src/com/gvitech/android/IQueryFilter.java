package com.gvitech.android;

public class IQueryFilter {
	protected long _piObject = 0;
	
	private native int createT();
	public static IQueryFilter create(){
		IQueryFilter qf = new IQueryFilter();
		qf._piObject = qf.createT();
		return qf;
	}	
	
	private native String getWhereClauseT(long queryFilter);
	public String getWhereClause()
	{
		return this.getWhereClauseT(this._piObject);
	}
	
	private native void setWhereClauseT(long queryFilter, String newVal);
	public void setWhereClause(String newVal)
	{
		this.setWhereClauseT(this._piObject, newVal);
	}
	public long get_piObject(){
		return _piObject;
	}
}
