package com.sapint.datatable;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.sap.conn.jco.JCoException;
import com.sapint.ReadWriteFile;
import com.sapint.SAPFunction;
import com.sapint.Utils.ReadTable;
import com.sapint.Utils.SAPException;

import data.DataTable;

public class DataTableUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDataTableToXml() {
		// TODO Auto-generated method stub
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
		dt.setRowCount(2);
		try {
			dt.Run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataTable result = dt.getResult();
		// System.out.println(result.getRows().size());
		// System.out.println(result.asXmlText());
		try {
			ReadWriteFile.creatTxtFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReadWriteFile.readTxtFile();
		// ReadWriteFile.writeTxtFile(result.asXmlText());
		String newXml = DataTableUtil.dataTableToXml(result);
		try {
			ReadWriteFile.writeTxtFile(newXml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testDataTableToJSON() {
		// TODO Auto-generated method stub
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
		//dt.addField("MANDT");
		//dt.addField("MATNR");
		//dt.addField("ERSDA");
		//dt.addField("ERNAM");
		try {
			dt.addCriteria("MATNR = '000000000000000011'");
		} catch (SAPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dt.setRowCount(10);
		try {
			dt.Run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataTable result = dt.getResult();

		String ResultAsJson = DataTableUtil.dataTableToJSON(result);
		System.out.println(ResultAsJson);
	}

}
