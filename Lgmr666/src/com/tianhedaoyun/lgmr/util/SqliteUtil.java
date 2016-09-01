package com.tianhedaoyun.lgmr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianhedaoyun.lgmr.bean.Point;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author sjw @version1.0
 * 
 */
public class SqliteUtil {
	static int op = 0;
	static SQLiteDatabase mydata = null;
	static String Path;

	// 1初始化,创建数据库，(注意指定全路径)

	public static SQLiteDatabase initial(String cachePath) {
		Path = cachePath;
		return SQLiteDatabase.openOrCreateDatabase(cachePath + "/sqlite.db3", null);
	}

	// 创建数据表:
	public static void createTable(String table) {
		try {
			mydata = initial(Path);
			// 创建表,注意主键命名,使用"_id"作为列名,以便使用SimpleCursorAdapter封装数据
			String sql = " create table " + table
					+ "(_id integer primary key autoincrement,oid text,PointRole text,type text,PointNumber text,x text,y text,z text,State text,comment text)";
			mydata.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 创建数据表:
	public static void createTableviews(String views) {
		try {
			mydata = initial(Path);
			// 创建表,注意主键命名,使用"_id"作为列名,以便使用SimpleCursorAdapter封装数据
			String sql = " create table " + views
					+ "(_id integer primary key autoincrement,name text,x text,y text,z text,heading text,tilt text,roll text)";
			mydata.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean exits(String table) {
		try {
			mydata = initial(Path);
			String sql = "select count(*)  from sqlite_master where type='table' and name = " + "'" + table + "'";
			Cursor cursor = mydata.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	// 插入数据
	public static void insertUseContentValues(SQLiteDatabase table, String tablename, Point point) {
		// TODO Auto-generated method stub
		table = initial(Path);
		ContentValues cv = new ContentValues();
		cv.put("oid", point.getOid());
		cv.put("PointRole", point.getPointRole());
		cv.put("type", point.getType());
		cv.put("PointNumber", point.getPointNumber());
		cv.put("x", point.getX());
		cv.put("y", point.getY());
		cv.put("z", point.getZ());
		cv.put("State", point.getState());
		cv.put("comment", point.getComment());
		table.insert(tablename, null, cv);
	}

	// 插入数据
	public static void insertUseContentValues(SQLiteDatabase table, String tablename, int oid, String PointRole,
			String type, String PointNumber, Double x, Double y, Double z, String State, String comment) {
		try {
			table = initial(Path);
			ContentValues cv = new ContentValues();
			cv.put("oid", oid);
			cv.put("PointRole", PointRole);
			cv.put("type", type);
			cv.put("PointNumber", PointNumber);
			cv.put("x", x);
			cv.put("y", y);
			cv.put("z", z);
			cv.put("State", State);
			cv.put("comment", comment);

			table.insert(tablename, null, cv);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mydata.close();
		}
	}

	public static void insertUseviews(SQLiteDatabase table, String tablename, String name, double[] array) {
		try {
			table = initial(Path);
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("x", array[0]);
			cv.put("y", array[1]);
			cv.put("z", array[2]);
			cv.put("heading", array[3]);
			cv.put("tilt", array[4]);
			cv.put("roll", array[5]);
			table.insert(tablename, null, cv);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mydata.close();
		}
	}

	// 查询所有数据
	public static List<Point> find(SQLiteDatabase table, String tablename) {
		table = initial(Path);
		List<Point> list = new ArrayList<Point>();
		Cursor c = table.query(tablename, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				Point point = new Point();
				int id = c.getInt(0);
				int oid = c.getInt(1);
				String pointRole = c.getString(2);
				String type = c.getString(3);
				String pointNumber = c.getString(4);
				double x = c.getDouble(5);
				double y = c.getDouble(6);
				double z = c.getDouble(7);
				String state = c.getString(8);
				String comment = c.getString(9);
				point.setOid(oid);
				point.setPointRole(pointRole);
				point.setPointNumber(pointNumber);
				point.setType(type);
				point.setState(state);
				point.setComment(comment);
				point.setX(x);
				point.setY(y);
				point.setZ(z);

				list.add(point);
				c.moveToNext();
			}
		}
		return list;
	}

	/*
	 * // 查询所有数据 public static List<Map<String, Object>> find(SQLiteDatabase
	 * table,String tablename) { table=initial(Path); List<Map<String,Object>>
	 * list=new ArrayList<Map<String,Object>>(); Cursor c =
	 * table.query(tablename, null, null, null, null, null, null); if
	 * (c.moveToFirst()) { for (int i = 0; i < c.getCount(); i++) { Map<String,
	 * Object> map=new HashMap<String, Object>(); int id = c.getInt(0); //String
	 * oid,String PointRole,String type,String PointNumber,Double x,Double
	 * y,Double z,String State String oid = c.getString(1); String PointRole =
	 * c.getString(2); String type = c.getString(3); String PointNumber =
	 * c.getString(4); String x = c.getString(5); String y = c.getString(6);
	 * String z = c.getString(7); String State=c.getString(8); String
	 * comment=c.getString(9); map.put("oid", oid); map.put("PointRole",
	 * PointRole); map.put("type", type); map.put("PointNumber", PointNumber);
	 * map.put("x", x); map.put("y", y); map.put("z", z); map.put("State",
	 * State); map.put("comment", comment); list.add(map); c.moveToNext(); } }
	 * return list; }
	 */

	// 查询点所有数据
	public static List<Map<String, Object>> find_dian(SQLiteDatabase table, String tablename, String s) {
		table = initial(Path);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor c = table.rawQuery("select * from " + tablename + " where State =" + s, null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				int id = c.getInt(0);
				// String oid,String PointRole,String type,String
				// PointNumber,Double x,Double y,Double z,String State
				int oid = c.getInt(1);
				String PointRole = c.getString(2);
				String type = c.getString(3);
				String PointNumber = c.getString(4);
				String x = c.getString(5);
				String y = c.getString(6);
				String z = c.getString(7);
				String State = c.getString(8);
				String comment = c.getString(9);
				map.put("oid", oid);
				map.put("PointRole", PointRole);
				map.put("type", type);
				map.put("PointNumber", PointNumber);
				map.put("x", x);
				map.put("y", y);
				map.put("z", z);
				map.put("State", State);
				map.put("comment", comment);
				list.add(map);
				c.moveToNext();
			}
		}
		return list;
	}

	public static List<Map<String, Object>> findviews(SQLiteDatabase table, String tablename) {
		table = initial(Path);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor c = table.query(tablename, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				int id = c.getInt(0);
				String name = c.getString(1);
				String x = c.getString(2);
				String y = c.getString(3);
				String z = c.getString(4);
				String heading = c.getString(5);
				String tilt = c.getString(6);
				String roll = c.getString(7);
				map.put("name", name);
				map.put("x", x);
				map.put("y", y);
				map.put("z", z);
				map.put("heading", heading);
				map.put("tilt", tilt);
				map.put("roll", roll);
				list.add(map);
				c.moveToNext();
			}
		}
		return list;
	}

	// 查询数据
	public static List<Point> findlike(SQLiteDatabase table, String tablename, String s) {
		table = initial(Path);
		List<Point> list = new ArrayList<Point>();
		Cursor c = table.rawQuery("select * from " + tablename + " where PointNumber like '%" + s + "%'", null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				int id = c.getInt(0);
				int oid = c.getInt(1);
				String pointRole = c.getString(2);
				String type = c.getString(3);
				String pointNumber = c.getString(4);
				double x = c.getDouble(5);
				double y = c.getDouble(6);
				double z = c.getDouble(7);
				String state = c.getString(8);
				String comment = c.getString(9);
				Point point = new Point(oid, pointRole, type, pointNumber, state, comment, x, y, z);
				list.add(point);
				c.moveToNext();
			}
		}
		return list;
	}
	
	// 判断是否为已经放样点
	public static List<Point> findlike_pd(SQLiteDatabase table, String tablename, int myoid,String mystate) {
			table = initial(Path);
			List<Point> list = new ArrayList<Point>();
			Cursor c = table.rawQuery("select * from " + tablename + " where oid = "+myoid+" and state = "+mystate, null);
			if (c.moveToFirst()) {
				for (int i = 0; i < c.getCount(); i++) {
					int id = c.getInt(0);
					int oid = c.getInt(1);
					String pointRole = c.getString(2);
					String type = c.getString(3);
					String pointNumber = c.getString(4);
					double x = c.getDouble(5);
					double y = c.getDouble(6);
					double z = c.getDouble(7);
					String state = c.getString(8);
					String comment = c.getString(9);
					Point point = new Point(oid, pointRole, type, pointNumber, state, comment, x, y, z);
					list.add(point);
					c.moveToNext();
				}
			}
			return list;
		}

	// 排序功能
	public static List<Point> find_sort(SQLiteDatabase table, String tablename, String s, String desc) {
		table = initial(Path);
		List<Point> list = new ArrayList<Point>();
		Cursor c = table.rawQuery("select * from " + tablename + " order by " + s + " " + desc, null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				int id = c.getInt(0);
				int oid = c.getInt(1);
				String pointRole = c.getString(2);
				String type = c.getString(3);
				String pointNumber = c.getString(4);
				double x = c.getDouble(5);
				double y = c.getDouble(6);
				double z = c.getDouble(7);
				String state = c.getString(8);
				String comment = c.getString(9);
				Point point = new Point(oid, pointRole, type, pointNumber, state, comment, x, y, z);
				list.add(point);
				c.moveToNext();
			}
		}
		return list;
	}

	// 筛选
	public static List<Point> find_filter(SQLiteDatabase table, String tablename, String s, String desc) {
		table = initial(Path);
		List<Point> list = new ArrayList<Point>();
		Cursor c = table.rawQuery("select * from " + tablename + " where " + s + " = " + "\'" + desc + "\'", null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				int id = c.getInt(0);
				int oid = c.getInt(1);
				String pointRole = c.getString(2);
				String type = c.getString(3);
				String pointNumber = c.getString(4);
				double x = c.getDouble(5);
				double y = c.getDouble(6);
				double z = c.getDouble(7);
				String state = c.getString(8);
				String comment = c.getString(9);
				Point point = new Point(oid, pointRole, type, pointNumber, state, comment, x, y, z);
				list.add(point);
				c.moveToNext();
			}
		}
		return list;
	}
	// 筛选
	public static List<Point> no_find_filter(SQLiteDatabase table, String tablename, String s, String desc) {
		table = initial(Path);
		List<Point> list = new ArrayList<Point>();
		Cursor c = table.rawQuery("select * from " + tablename + " where " + s + " is not " + "\'" + desc + "\'", null);
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				int id = c.getInt(0);
				int oid = c.getInt(1);
				String pointRole = c.getString(2);
				String type = c.getString(3);
				String pointNumber = c.getString(4);
				double x = c.getDouble(5);
				double y = c.getDouble(6);
				double z = c.getDouble(7);
				String state = c.getString(8);
				String comment = c.getString(9);
				Point point = new Point(oid, pointRole, type, pointNumber, state, comment, x, y, z);
				list.add(point);
				c.moveToNext();
			}
		}
		return list;
	}

	public static List<Point> findByName(SQLiteDatabase mydata, String tablename, String pointNumber1) {
		if (pointNumber1 == null) {
			return null;
		} else {
			// TODO Auto-generated method stub
			mydata = initial(Path);
			List<Point> list = new ArrayList<Point>();
			Cursor c = mydata.rawQuery("select * from " + tablename + " where PointNumber =" + "'" + pointNumber1 + "'",
					null);
			if (c.moveToFirst()) {
				for (int i = 0; i < c.getCount(); i++) {
					Point point = new Point();
					int id = c.getInt(0);
					int oid = c.getInt(1);
					String pointRole = c.getString(2);
					String type = c.getString(3);
					String pointNumber = c.getString(4);
					double x = c.getDouble(5);
					double y = c.getDouble(6);
					double z = c.getDouble(7);
					String state = c.getString(8);
					String comment = c.getString(9);
					point.setOid(oid);
					point.setPointRole(pointRole);
					point.setPointNumber(pointNumber);
					point.setType(type);
					point.setState(state);
					point.setComment(comment);
					point.setX(x);
					point.setY(y);
					point.setZ(z);
					list.add(point);
					c.moveToNext();
				}
			}
			return list;
		}
	}

	// 修改方法(一)
	public static void update(SQLiteDatabase db, String tablename, String state, String pointNumber) {
		try {
			db = initial(Path);
			String sql = " update " + tablename + " set state='" + state + "'" + "where PointNumber =" + "'"
					+ pointNumber + "'";
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	// 修改方法(二)
	public void updateUseContentValues(SQLiteDatabase db) {
		try {
			db = initial(Path);
			ContentValues values = new ContentValues();
			values.put("password", "999");
			String whereClause = "_id=?";
			String[] whereArgs = { String.valueOf(1) };
			db.update("student", values, whereClause, whereArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	// 删除方法(一)
	public static void delete2(SQLiteDatabase db, String table, String name) {
		try {
			db = initial(Path);
			String sql = " delete from " + table + " where name = " + name;
			System.out.println("del--" + sql);
			db.execSQL(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	// 删除方法
	public static void delete(SQLiteDatabase db, String table, String name) {
		try {
			db = initial(Path);
			String whereClause = "name=?";
			String[] whereArgs = { name };
			db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	// 删除方法
	public static void delete_dian(SQLiteDatabase db, String table, String name) {
		try {
			db = initial(Path);
			String whereClause = "PointNumber=?";
			String[] whereArgs = { name };
			db.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

}