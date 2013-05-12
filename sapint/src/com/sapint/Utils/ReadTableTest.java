package com.sapint.Utils;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sap.conn.jco.JCoException;
import com.sapint.ReadWriteFile;
import com.sapint.SAPFunction;

import data.DataTable;

public class ReadTableTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadTable() {
		try {
			SAPFunction.step1Connect();
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadTable dt = null;
		try {
			dt = new ReadTable(SAPFunction.ABAP_AS_POOLED);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dt.SetCustomFunctionName("Z_XTRACT_IS_TABLE");
		dt.setTableName("MARA");
		dt.addField("MANDT");
		dt.addField("MATNR");
		dt.addField("ERSDA");
		dt.addField("ERNAM");
		dt.setRowCount(30);
		try {
			dt.Run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataTable result = dt.getResult();
		System.out.println(result.getRows().size());
		System.out.println(result.asXmlText());
		try {
			ReadWriteFile.creatTxtFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ReadWriteFile.readTxtFile();
        try {
			ReadWriteFile.writeTxtFile(result.asXmlText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddField() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testGetAllFieldsOfTable() {
	//	fail("Not yet implemented");
	}

}
