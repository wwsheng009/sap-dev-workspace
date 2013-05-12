package com.sapint.ws;
import com.sap.conn.jco.JCoException;

import javax.jws.WebService; 
import javax.xml.ws.Endpoint; 

import com.sapint.ReadWriteFile;
import com.sapint.SAPFunction;
import com.sapint.Utils.ReadTable;

import data.DataTable;

@WebService
public class RfcWS {

	/**
	 * 根据函数名，在SAP中搜索相关的函数列表，返回的是一Json格式。
	 * @param functionName
	 * @return
	 */
	public String getRFCFunctionLIst(String functionName){
		try{
			SAPFunction.step1Connect();
			String s = SAPFunction.getRFCfunctionList(SAPFunction.ABAP_AS_POOLED, functionName);
			return s;
		}catch(JCoException e){
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost:8888/sapint/getRFCFunctionLIst", new RfcWS()); 
		System.out.println("服务开启成功！！");
	}

}
