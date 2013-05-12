package com.sapint.ws;

import javax.xml.ws.Endpoint;

public class PublishWS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost:8888/sapint/getRFCFunctionLIst", new RfcWS()); 
		System.out.println("RfcWS 服务开启成功！！");
		Endpoint.publish("http://localhost:8888/sapint/RFCReadTable", new RfcReadTableWS());
		System.out.println("RfcReadTableWS 服务开启成功！！");
	}

}
