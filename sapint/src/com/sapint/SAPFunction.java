package com.sapint;

import data.DataColumn;
import data.DataException;
import data.DataRow;
import data.DataTable;

import java.util.HashMap;
import java.util.Properties;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.*;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoListMetaData;
import com.sap.conn.jco.JCoRecordFieldIterator;
import com.sap.conn.jco.JCoRecordMetaData;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sapint.CustomDestinationDataProvider.MyDestinationDataProvider;

public class SAPFunction {

	static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
	public static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static String ABAP_MS = "ABAP_MS_WITHOUT_POOL";
	static boolean alreadyRegister = false;

	public static void step1Connect() throws JCoException {

		if (SAPFunction.alreadyRegister == true) {
			return;
		}
		MyDestinationDataProvider myProvider = new MyDestinationDataProvider();

		try {
			com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(myProvider);
			alreadyRegister = true;
		} catch (IllegalStateException providerAlreadyRegisteredException) {
			throw new Error(providerAlreadyRegisteredException);

		}

		// CustomDestinationDataProvider test = new
		// CustomDestinationDataProvider();
		myProvider.changeProperties(ABAP_AS_POOLED, CustomDestinationDataProvider.getDestinationPropertiesFromUI());

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		System.out.println("Attributes:");
		System.out.println(destination.getAttributes());
		System.out.println();

	}

	// 在同一个服务器下修改用户名与密码进行登录。
	public static String login(String usname, String password, String client, String lang) throws JCoException {
		Properties connectProperties = new Properties();
		// connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
		// "192.168.0.205");
		// connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
		// "00");
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, client);
		connectProperties.setProperty(DestinationDataProvider.JCO_USER, usname);
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, password);
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, lang);

		try {
			MyDestinationDataProvider myProvider = new MyDestinationDataProvider();
			myProvider.changeProperties("", connectProperties);
			JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);

			JCoFunction function = destination.getRepository().getFunction("RFC_PING");
			function.execute(destination);
			return "SUCESS";
		} catch (JCoException e) {

			System.out.println(e.getMessage());
			return "FAILED";
		}
	}

	/**
	 * 测试系统的连通性。
	 * @throws JCoException
	 */
	public static void callFunction1() throws JCoException {
		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
		if (function == null)
			throw new RuntimeException("STFC_CONNECTION not found in SAP.");
		

		try {
			function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");
			function.execute(destination);
		} catch (AbapException e) {
			System.out.println(e.toString());
			return;
		}

		System.out.println("STFC_CONNECTION finised:");
		System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
		// System.out.println(" Reponse: " + function.getExportParameterList().)

	}

	/**
	 * 检查某个指定的函数是否存在于SAP系统里
	 * @param sysName 连接池中的SAP名字
	 * @param functionName	函数名字
	 * @return
	 * @throws JCoException
	 */
	public static boolean checkFunction(String sysName, String functionName) throws JCoException {

		try {
			String _name = functionName;
			JCoDestination destination = JCoDestinationManager.getDestination(sysName);
			JCoFunction function = destination.getRepository().getFunction("RFC_FUNCTION_SEARCH");
			function.getImportParameterList().setValue("FUNCNAME", _name);
			function.execute(destination);
			JCoTable functions = function.getTableParameterList().getTable("FUNCTIONS");
			if (functions.getNumRows() == 1) {
				return true;
			} else
				return false;

		} catch (AbapException e) {
			// throw new JCoException(1,e.getKey(),e.getMessage());
			System.out.println(e.toString());
			return false;
		}

	}

	/**
	 * 把Rfc表转换成json，这里使用的是中间键自带的方法。先转换成xml，再转成Json.
	 * 
	 * @param rfcTable
	 * @return
	 */
	// 把JCoTable转换成Json格式的字段串。
	public static String RfcTableToJSon(JCoTable rfcTable) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(rfcTable.toXML());
		return json.toString(1); // 格式化文本
	}

	/**
	 * 把JcoTable转换成DataTable
	 * @param rfcTable
	 * @return
	 * @throws DataException
	 */
	public static DataTable GetDataTableFromRfcTable(JCoTable rfcTable) throws DataException {
		DataTable dtRet = new DataTable();
		JCoRecordFieldIterator iterator = rfcTable.getRecordFieldIterator();
		// rfcTable.getFieldIterator()
		while (iterator.hasNextField()) {
			JCoField field = iterator.nextRecordField();
			String name = field.getName();
			String typeName = field.getTypeAsString();
			System.out.println(name);
			DataColumn column = new DataColumn();
			column.setColumnName(name);
			column.setDataTypeName(typeName);

			dtRet.getColumns().add(column);
		}
		rfcTable.firstRow();
		for (int i = 0; i < rfcTable.getNumRows(); i++, rfcTable.nextRow()) {
			rfcTable.setRow(i);
			DataRow row = dtRet.newRow();
			for (int liElement = 0; liElement < rfcTable.getFieldCount(); liElement++) {
				// System.out.println(rfcTable.getString(liElement));
				// System.out.println();

				row.setObject(liElement, rfcTable.getValue(liElement));
				// row.setString(liElement, rfcTable.getString(liElement));
				// System.out.println(row.getObject(liElement));
			}
			try {
				// System.out.println(row.toString());
				dtRet.getRows().add(row);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return dtRet;
	}

	/**
	 * 把RfcTable转换成DataTable，但是与转换方式不一样。
	 * @param rfcTable
	 * @return
	 */
	public static DataTable GetDataTableFromRfcTable1(JCoTable rfcTable) {
		DataTable dtRet = new DataTable();

		for (int liElement = 0; liElement < rfcTable.getFieldCount(); liElement++) {
			String fieldName = rfcTable.getRecordMetaData().getRecordMetaData(liElement).getName();
			DataColumn col = new DataColumn();
			col.setColumnName(fieldName);
			String typeName = rfcTable.getRecordMetaData().getRecordTypeName(liElement);
			col.setDataTypeName(typeName);

			// RfcElementMetadata rfcEMD =
			// rfcTable.GetElementMetadata(liElement);
			dtRet.getColumns().add(col);
		}
		for (int i = 0; i < rfcTable.getNumRows(); i++, rfcTable.nextRow()) {
			rfcTable.setRow(i);
			DataRow row = dtRet.newRow();
			for (int liElement = 0; liElement < rfcTable.getFieldCount(); liElement++) {
				try {
					row.setObject(liElement, rfcTable.getValue(liElement));
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				dtRet.getRows().add(row);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dtRet;
	}
	
	/**
	 * 把函数的元数据转换成哈希表。
	 * @param jcoListMetaData
	 * @return
	 */
	private static HashMap<String, HashMap<String, Object>> getJCoListMetaData(JCoListMetaData jcoListMetaData) {
		if (jcoListMetaData == null)
			return null;
		System.out.println("Content of " + jcoListMetaData.getName());
		System.out.println(jcoListMetaData.toString());
		HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
		for (int i = 0; i < jcoListMetaData.getFieldCount(); i++) {
			HashMap<String, Object> record = new HashMap<String, Object>();

			if (jcoListMetaData.isAbapObject(i)) {

				map.put(jcoListMetaData.getName(i), record);

			} else if (jcoListMetaData.isNestedType1Structure(i) || jcoListMetaData.isStructure(i)) {
				JCoRecordMetaData recordmeta = jcoListMetaData.getRecordMetaData(i);
				HashMap<String, String> maprecord = new HashMap<String, String>();
				for (int r = 0; r < recordmeta.getFieldCount(); r++) {
					maprecord.put("Name", recordmeta.getName(i));
					maprecord.put("ByteLength", String.valueOf(recordmeta.getByteLength(i)));
					maprecord.put("ClassName", recordmeta.getClassNameOfField(i));
					maprecord.put("Decimals", String.valueOf(recordmeta.getDecimals(i)));
					// maprecord.put("Default", recordmeta.getDefault(i));
					maprecord.put("Description", recordmeta.getDescription(i));
					maprecord.put("Length", String.valueOf(recordmeta.getLength(i)));
					// maprecord.put("RecordFieldName",
					// recordmeta.getRecordFieldName(i));
					maprecord.put("RecordTypeName", recordmeta.getRecordTypeName(i));
					maprecord.put("Type", String.valueOf(recordmeta.getType(i)));
					maprecord.put("TypeAsString", recordmeta.getTypeAsString(i));
					maprecord.put("UnicodeByteLength", String.valueOf(recordmeta.getUnicodeByteLength(i)));
					record.put(recordmeta.getName(i), maprecord);

				}

				map.put(recordmeta.getName(), record);

			} else if (jcoListMetaData.isTable(i)) {
				System.out.println("The talbe is :" + jcoListMetaData.getName(i));
				JCoRecordMetaData recordmeta = jcoListMetaData.getRecordMetaData(i);
				HashMap<String, String> maprecord = new HashMap<String, String>();
				System.out.println("Table fields count: " + recordmeta.getFieldCount());

				for (int r = 0; r < recordmeta.getFieldCount(); r++) {
					// maprecord.put(recordmeta.getName(r),recordmeta.getTypeAsString(r));
					maprecord.put("Name", recordmeta.getName(r));
					maprecord.put("ByteLength", String.valueOf(recordmeta.getByteLength(r)));
					maprecord.put("ClassName", recordmeta.getClassNameOfField(r));
					maprecord.put("Decimals", String.valueOf(recordmeta.getDecimals(r)));
					// maprecord.put("Default", recordmeta.getDefault(r));
					maprecord.put("Description", recordmeta.getDescription(r));
					maprecord.put("Length", String.valueOf(recordmeta.getLength(r)));
					// maprecord.put("RecordFieldName",
					// recordmeta.getRecordFieldName(i));
					maprecord.put("RecordTypeName", recordmeta.getRecordTypeName(r));
					maprecord.put("Type", String.valueOf(recordmeta.getType(r)));
					maprecord.put("TypeAsString", recordmeta.getTypeAsString(r));
					maprecord.put("UnicodeByteLength", String.valueOf(recordmeta.getUnicodeByteLength(r)));
					record.put(recordmeta.getName(r), maprecord);
				}

				map.put(jcoListMetaData.getName(i), record);
			} else {
				record.put("Name", jcoListMetaData.getName(i));
				record.put("ByteLength", String.valueOf(jcoListMetaData.getByteLength(i)));
				record.put("ClassName", jcoListMetaData.getClassNameOfField(i));
				record.put("Decimals", String.valueOf(jcoListMetaData.getDecimals(i)));
				record.put("Default", jcoListMetaData.getDefault(i));
				record.put("Description", jcoListMetaData.getDescription(i));
				record.put("Length", String.valueOf(jcoListMetaData.getLength(i)));
				record.put("RecordFieldName", jcoListMetaData.getRecordFieldName(i));
				record.put("RecordTypeName", jcoListMetaData.getRecordTypeName(i));
				record.put("Type", String.valueOf(jcoListMetaData.getType(i)));
				record.put("TypeAsString", jcoListMetaData.getTypeAsString(i));
				record.put("UnicodeByteLength", String.valueOf(jcoListMetaData.getUnicodeByteLength(i)));

				map.put(jcoListMetaData.getName(i), record);

			}

		}

		return map;
	}
	/**
	 * 测试读取RFC函数的所有的函数的元数据,返回JSON格式
	 * @param sysName
	 * @param funame
	 * @param outJson
	 * @return
	 */
	public static boolean getFuncMeta(String sysName, String funame, String outJson) {
		try {
			JCoDestination destination = JCoDestinationManager.getDestination(sysName);
			if (funame == null || funame == "") {
				outJson = null;
				return false;
			}
			if (!checkFunction(sysName, funame)) {
				outJson = null;
				return false;
			}

			funame = funame.toUpperCase();

			destination.getRepository().clear();
			JCoFunctionTemplate functionTamplate = destination.getRepository().getFunctionTemplate(funame);
			JCoListMetaData importlist = functionTamplate.getImportParameterList();
			JCoListMetaData exportlist = functionTamplate.getExportParameterList();
			JCoListMetaData changelist = functionTamplate.getChangingParameterList();
			JCoListMetaData tablelist = functionTamplate.getTableParameterList();
			// ArrayList paraImport = new ArrayList();

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Import","");
			map.put("Export","");
			map.put("Change","");
			map.put("Table","");
			
			if (importlist != null) {
				System.out.println("Importlist" + importlist.toString());
				map.put("Import", getJCoListMetaData(importlist));
			}

			if (exportlist != null) {
				map.put("Export", getJCoListMetaData(tablelist));
				System.out.println("Export" + exportlist.toString());
			}
			if (changelist != null) {
				map.put("Change", getJCoListMetaData(tablelist));
			}
			if (tablelist != null) {
				map.put("Table", getJCoListMetaData(tablelist));
			}

			JSONObject o = JSONObject.fromObject(map);
			System.out.println((o.toString()));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 封装SAP函数RFC_FUNCTION_SEARCH,并返回函数列表的JSon格式。
	 * @param sysName
	 * @param functionName
	 * @return
	 * @throws JCoException
	 */
	public static String getRFCfunctionList(String sysName, String functionName) throws JCoException {
		try {

			JCoDestination destination = JCoDestinationManager.getDestination(sysName);
			String _funame = functionName; // "*" + functionName + "*"
											// ;//String.format("*{1}*",
											// functionName);
			JCoFunction rfc_function_search = destination.getRepository().getFunction("RFC_FUNCTION_SEARCH");
			rfc_function_search.getImportParameterList().setValue("FUNCNAME", _funame);
			rfc_function_search.execute(destination);
			JCoTable functions = rfc_function_search.getTableParameterList().getTable("FUNCTIONS");
			if (!functions.isEmpty()) {
				// DataTable dt = GetDataTableFromRfcTable(functions);
				// System.out.println(RfcTableToJSon(functions));
				return (RfcTableToJSon(functions));
			}
			return "";
		} catch (JCoException e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	public static void main(String[] args) throws JCoException {

	}


}
