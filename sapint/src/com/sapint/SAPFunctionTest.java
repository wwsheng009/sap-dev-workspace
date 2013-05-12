package com.sapint;

import org.junit.Before;
import org.junit.Test;

import com.sap.conn.jco.JCoException;

public class SAPFunctionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStep1Connect() {
		try {
			SAPFunction.step1Connect();
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	fail("Not yet implemented");
	}

	@Test
	public void testCallFunction1() {
		try {
			SAPFunction.step1Connect();
			SAPFunction.callFunction1();
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	fail("Not yet implemented");
	}

	@Test
	public void testCheckFunction() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testRfcTableToJSon() {
	//	fail("Not yet implemented");
	}
	
	@Test
	public void testGetFuncMeta(){
		try {
			SAPFunction.step1Connect();
			// getRFCfunctionList(ABAP_AS_POOLED,"RFC*");
			SAPFunction.getFuncMeta(SAPFunction.ABAP_AS_POOLED, "RFC_READ_TABLE", "");
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
