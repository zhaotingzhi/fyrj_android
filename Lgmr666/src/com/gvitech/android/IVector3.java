package com.gvitech.android;

public class IVector3 {
	
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
	public IVector3(double x, double y, double z){
		this.set(x, y, z);
	}

	public IVector3(){
		
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public void setComponent(int i, double val)	  {
	    if (i == 0)
	      this.x = val;
	    else if (i == 1)
	      this.y = val;
	    else
	      this.z = val;
	  }

	  public void setZero()	  {
	    this.x = (this.y = this.z = 0.0D);
	  }

	  public void set(IVector3 other)	  {
	    this.x = other.x;
	    this.y = other.y;
	    this.z = other.z;
	  }

	  public void scale(double s)	  {
	    this.x *= s;
	    this.y *= s;
	    this.z *= s;
	  }

	  public void normalize()	  {
	    double d = length();
	    if (d != 0.0D)
	      scale(1.0D / d);
	  }

	  public static double dot(IVector3 a, IVector3 b)	  {
	    return a.x * b.x + a.y * b.y + a.z * b.z;
	  }

	  public double length()	  {
	    return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	  }

	  public boolean sameValues(IVector3 other)	  {
		  return (this.x == other.x) && (this.y == other.y) && (this.z == other.z);
	  }

	  public static void add(IVector3 a, IVector3 b, IVector3 result)	  {
		  result.set(a.x + b.x, a.y + b.y, a.z + b.z);
	  }
	  
	  public static void sub(IVector3 a, IVector3 b, IVector3 result)	  {
		  result.set(a.x - b.x, a.y - b.y, a.z - b.z);
	  }

	  public static void cross(IVector3 a, IVector3 b, IVector3 result)	  {
		  result.set(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	  }

	  public static void ortho(IVector3 v, IVector3 result)	  {
	    int k = largestAbsComponent(v) - 1;
	    if (k < 0) {
	      k = 2;
	    }
	    result.setZero();
	    result.setComponent(k, 1.0D);

	    cross(v, result, result);
	    result.normalize();
	  }

	  public static int largestAbsComponent(IVector3 v)	  {
	    double xAbs = Math.abs(v.x);
	    double yAbs = Math.abs(v.y);
	    double zAbs = Math.abs(v.z);

	    if (xAbs > yAbs) {
	      if (xAbs > zAbs) {
	        return 0;
	      }
	      return 2;
	    }

	    if (yAbs > zAbs) {
	      return 1;
	    }
	    return 2;
	  }	
}
