package com.sapint.ws;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.sap.conn.jco.JCoException;
import com.sapint.SAPFunction;
import com.sapint.Utils.ReadTable;
import com.sapint.Utils.SAPException;

import data.DataTable;
@WebService
public class RfcReadTableWS {
	private ArrayList<String> fields;
	private ArrayList<String> options;
	
	RfcReadTableWS(){
		this.fields = new ArrayList<String>();
		this.options = new ArrayList<String>();
	}
	
	
	public void addField(String field){
		
		try{
			ArrayList<String> list = (ArrayList<String>)JSONArray.toCollection(JSONObject.fromObject(field).getJSONArray("fields"));
			this.fields = list;
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		//this.fields.add(field);
	}
	public void addCriteria(String sql){
		try{
			ArrayList<String> list = (ArrayList<String>)JSONArray.toCollection(JSONObject.fromObject(sql).getJSONArray("sql"));	
			this.options = list;
		}catch(JSONException e){
			
			e.printStackTrace();
		}
		
		
//		this.options.add(sql);
	}
	public void reset(){
		this.fields.clear();
		this.fields.clear();
	}
	
	public String getDataTable(String tableName,int rowCount,int rowSkip){
		try{
			SAPFunction.step1Connect();
			ReadTable dt;
			dt = new ReadTable(SAPFunction.ABAP_AS_POOLED);
			dt.SetCustomFunctionName("Z_XTRACT_IS_TABLE");
			if(tableName!=""){
				dt.setTableName(tableName.toUpperCase());
			}else
			{
				return "";
			}
//			dt.addField("MANDT");
//			dt.addField("MATNR");
//			dt.addField("ERSDA");
//			dt.addField("ERNAM");
			if (rowCount > 0 )dt.setRowCount(rowCount);
			if (rowSkip > 0 )dt.setRowSkip(rowSkip);
			for(String field : this.fields){
				dt.addField(field);
			}
			for(String sql : this.options){
				dt.addCriteria(sql);
			}
			try {
				dt.Run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataTable result = dt.getResult();
			//System.out.println("public String getDataTable(String tableName,int rowCount,int rowSkip){");
			//System.out.println(result.getRows().size());
			//System.out.println(result.asXmlText());
			//return com.sapint.datatable.DataTableUtil.dataTableToXml(result);
			return com.sapint.datatable.DataTableUtil.dataTableToJSON(result);
			//ReadWriteFile.creatTxtFile();
	       // ReadWriteFile.readTxtFile();
	       // ReadWriteFile.writeTxtFile(result.asXmlText());
		}catch(JCoException e){
			e.printStackTrace();
			return "";
		} catch (SAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost:8889/sapint/RFCReadTable", new RfcReadTableWS()); 
		System.out.println("服务开启成功！！");
	}
	
}
