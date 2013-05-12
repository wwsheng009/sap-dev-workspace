package com.test;
//import 你自己需要的包;
import data.DataColumn;
import data.DataColumnCollection;
import data.DataRow;
import data.DataTable;
import data.common.DataAdapter;
import data.common.JdbcAdapter;
//import dataQuery.ComplexQuery;
//import dataQuery.DataConnection;

public class testdatatable {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		//ResultSet rs = 用你的方法从数据库中取到你需要的ResultSet;
		
               //使用datatable
		DataTable dtb = new DataTable();
		data.common.JdbcAdapter dAdapter = new JdbcAdapter();
	//	dAdapter.fillDataTable(dtb, rs);
		//给一行赋值
		dtb.getRows().get(0).setString(3, "test");
		
		//循环显示数据
		for (int i = 0; i < dtb.getRows().size(); i++) {
			DataRow row = dtb.newRow();
			row = dtb.getRows().get(i);
			DataColumnCollection ccc=dtb.getColumns();
			//增加列
			DataColumn dtmColumn=new DataColumn("mytest", 1);
			ccc.add(dtmColumn);
			//显示一行的每列数据
			for (int j = 0; j < ccc.size(); j++) {
				//对增加的列赋值
				row.setString(4, "25");
				//输出
				System.out.println(row.getString(j));
			}
		}
		//rs.close();


	}

}
