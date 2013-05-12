package com.sapint.Utils;

import java.util.ArrayList;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sapint.Converts;
import data.DataColumn;
import data.DataRow;
import data.DataTable;

public class ReadTable {
	private String _Delimiter;
	private int _FetchedRows;
	private ReadTableFieldCollection _Fields;// 读出的字段列表,接口调用后，就是读出表的字段列表
	private ArrayList<String> fields;	// 需要修传入的字段列表，告诉函数需要筛选哪些字段
	private String _FunctionName;
	private String _LastPrimaryKey;
	private int _PackageSize;
	// private ReadTableFieldCollection _PrimaryKeys;
	private ReadTableFieldCollection _PrimaryKeys;
	private boolean _RaiseIncomingPackageEvent;
	private int _RowCount;
	private int _RowSkip;
	private String _TableName;
	private boolean _UsePrimaryKeyPackaging;
	private String _WhereClause;
	private int anzahlaufrufe;
	private boolean BackgroundExtraction;
	private String BackgroundRequestID;
	private String BufferLocation;
	// private RfcDestination des;
	private JCoDestination des;

	// private OnIncomingPackage IncomingPackage;
	private boolean OHSExtraction;
	private int OHSLastPackageNr;
	private int OHSRequestID;
	private ArrayList<String> options;
	// private OnPackageProgress PackageProgress;
	private DataTable t;

	/**
	 * 构造函数
	 * @param sysName	系统名称。
	 * @throws JCoException
	 */
	public ReadTable(String sysName) throws JCoException {
		this.fields = new ArrayList<String>();
		this.options = new ArrayList<String>();
		this._PrimaryKeys = new ReadTableFieldCollection();
		this._TableName = "";
		this._Fields = new ReadTableFieldCollection();
		this._WhereClause = "";
		this._Delimiter = "";
		this._FunctionName = "";
		this.BackgroundRequestID = "";
		this.BufferLocation = "";
		this._LastPrimaryKey = "";
		// this.des = SAPDestination.GetDesByName(sysName);
		this.des = JCoDestinationManager.getDestination(sysName);
		// if (Connection.Codepage.Equals("8000"))
		// {
		// this._Delimiter = "|";
		// }
		// Connection.Log("ReadTable() Connection.Codepage = " +
		// Connection.Codepage);
	}

	public void ActivateBackgroundExtraction(String BackgroundRequestID,String BufferLocation) {
		this.BackgroundRequestID = BackgroundRequestID;
		this.BufferLocation = BufferLocation;
		this.BackgroundExtraction = true;
	}

	public void ActivateOHSExtraction(int RequestID) {
		this.OHSExtraction = true;
		this.OHSRequestID = RequestID;
		this._PackageSize = 0xc350;
	}

	/**
	 * 增加一个Sql语句。
	 * @param SQL
	 * @throws SAPException
	 */
	public void addCriteria(String SQL) throws SAPException {
		if (SQL.length() > 0x47) {
			throw new SAPException("Messages.SQLtoolong");
		}
		this.options.add(SQL.toUpperCase());
	}

	/**
	 * 增加一个需要读取的字段。
	 * @param FieldName	字段名称
	 */
	public void addField(String FieldName) {
		this.fields.add(FieldName.toUpperCase().trim());
	}

	private void addWhereLine(JCoTable toptions, String whereline) {
		toptions.appendRow();
		toptions.setValue("TEXT", whereline);
	}

	private void executeRFC_READ_TABLE(JCoFunction f) throws JCoException {
		if (this.BackgroundExtraction) {
			f.getImportParameterList().setValue("ACTIONID", "D");
			f.getImportParameterList().setValue("REQUESTID",
					this.BackgroundRequestID);
			if (!this.BufferLocation.equals("")) {
				f.getImportParameterList().setValue("LCTN", this.BufferLocation);
			}
		}
		JCoTable toptions = f.getTableParameterList().getTable("OPTIONS");
		this.initWhereClause(toptions);
		this.anzahlaufrufe++;
		if ((this._UsePrimaryKeyPackaging && (this.anzahlaufrufe > 1))
				&& ((Integer.parseInt(f.getImportParameterList().getValue("ROWCOUNT").toString())) > 0)) {
			f.getImportParameterList().setValue("ROWCOUNT",Integer.parseInt(f.getImportParameterList()
							.getValue("ROWCOUNT").toString()) + 1);
		}
		f.getImportParameterList().setValue("DELIMITER", this.getDelimiter());
		// if (this.con.Logging)
		// {
		// try
		// {
		// f.SaveToXML("ReadTableBeforeCall_" + this.anzahlaufrufe.ToString() +
		// ".xml");
		// }
		// catch
		// {
		// }
		// }
		if (this._UsePrimaryKeyPackaging) {
			f.getImportParameterList().setValue("ROWSKIPS", "0");
		}
		f.execute(des);

	}

	private void initWhereClause(JCoTable toptions) {
		toptions.clear();

		if (this._WhereClause.equals("")) {
			for (int i = 0; i < this.options.size(); i++) {
				toptions.appendRow();
				toptions.setValue("TEXT", this.options.get(i).toString());
			}
		} else {
			for (int j = 0; j < this._WhereClause.length(); j += 0x48) {
				if ((this._WhereClause.length() - j) > 0x48) {
					toptions.appendRow();
					boolean flag = false;
					for (int k = 0; k < 0x47; k++) {
						if (this._WhereClause.substring((j + 0x47) - k, 1).equals(" ")) {
							toptions.setValue("TEXT",this._WhereClause.substring(j, 0x48 - k));
							j -= k;
							flag = true;
							k = 0x48;
						}
					}
					if (!flag) {
						toptions.setValue("TEXT",this._WhereClause.substring(j, 0x48));
					}
				} else {
					toptions.appendRow();
					toptions.setValue("TEXT", this._WhereClause.substring(j));
				}

			}
			String str = "";
			for (int i = 0; i < toptions.getNumRows(); i++, toptions.nextRow()) {
				toptions.setRow(i);
				str = str + toptions.getValue("TEXT").toString() + "\r\n";
			}

		}
		if (this._UsePrimaryKeyPackaging) {
			if (toptions.getNumRows() == 0) {
				this.addWhereLine(toptions, "(");
			} else {
				this.addWhereLine(toptions, "AND (");
			}
			for (int m = 0; m < this._PrimaryKeys.size(); m++) {
				String whereline = this._PrimaryKeys.get(m).getFieldName()
						+ " >= '" + this._PrimaryKeys.get(m).LastKeyValue + "'";
				if (m == 0) {
					whereline = "( " + whereline;
				} else {
					whereline = "AND " + whereline;
				}
				if (m == (this._PrimaryKeys.size() - 1)) {
					whereline = whereline + " )";
				}
				this.addWhereLine(toptions, whereline);
			}
			for (int n = 0; n < this._PrimaryKeys.size(); n++) {
				for (int num6 = n; num6 >= 0; num6--) {
					String str3;
					if (num6 == n) {
						str3 = "OR ( "
								+ this._PrimaryKeys.get(num6).getFieldName()
								+ " > '"
								+ this._PrimaryKeys.get(num6).LastKeyValue
								+ "'";
					} else {
						str3 = "AND "
								+ this._PrimaryKeys.get(num6).getFieldName()
								+ " >= '"
								+ this._PrimaryKeys.get(num6).LastKeyValue
								+ "'";
					}
					if (num6 == 0) {
						str3 = str3 + " )";
					}
					this.addWhereLine(toptions, str3);
				}
			}
			toptions.appendRow();
			toptions.setValue("TEXT", ")");
		}
	}

	/**
	 * 把返回的JcoTable进行处理。
	 * @param t
	 * @param f
	 * @throws Exception
	 */
	private void ProcessRetrievdData(DataTable t, JCoFunction f)
			throws Exception {
		// this.con.Log("Enter ProcessRetrievdData; LastPrimaryKey=" +
		// this._LastPrimaryKey);
		String str = "";
		JCoTable table = f.getTableParameterList().getTable("FIELDS");
		JCoTable tableDATA = f.getTableParameterList().getTable("DATA");
		for (int i = 0; i < tableDATA.getNumRows(); i++) {
			tableDATA.setRow(i);
			String str2 = tableDATA.getValue(0).toString();
			f.getTableParameterList().getTable("DATA").getMetaData().getLength(0);
			String[] strArray = null;
			if (this.getUsePrimaryKeyPackaging()) {
				str = str2.substring(0, this._PrimaryKeys.GetOverallLength());
				if (i >= (f.getTableParameterList().getTable("DATA")
						.getNumRows() - 1)) {
					int startIndex = 0;
					for (ReadTableField field : this._PrimaryKeys) {
						field.LastKeyValue = str.substring(startIndex,
								field.getLength());
						startIndex += field.getLength();
					}
				}
			}

			if (!this.getUsePrimaryKeyPackaging()
					|| (this._LastPrimaryKey != str)) {
				DataRow row = t.newRow();
				if (!this._Delimiter.equals("")) {
					strArray = str2.split(this._Delimiter);
				}
				if (this._UsePrimaryKeyPackaging) {
					for (int j = this._PrimaryKeys.size(); j < table
							.getNumRows(); j++) {
						if (this._Delimiter.equals("")) {
							table.setRow(j);

							int startindex = Integer.parseInt(table.getValue("OFFSET").toString());
							int endindex = Integer.parseInt(table.getValue("LENGTH").toString());
							//System.out.print("Field:"+ table.getValue(0).toString().trim());
							//System.out.print(" OFFSET " + startindex);
							//System.out.println(" LENGTH " + endindex);
							String s = "";
							if(startindex + endindex - 1 == str2.length()){
								s = str2.substring(startindex, startindex + endindex - 1 );
							}else if(startindex + endindex <=str2.length())
							{
								s = str2.substring(startindex, startindex + endindex  );
							}
							//System.out.println(s.trim());
							row.setObject(table.getValue(0).toString().trim(),s.trim());
						} else if (strArray.length > j) {
							table.setRow(j);
							// row[table[j].GetValue(0).ToString().Trim()] =
							// strArray[j].trim();
							row.setObject(table.getValue(0).toString().trim(),
									strArray[j].trim());
						}
					}
				} else {
					//System.out.print("Stre length: " + str2.length());
					for (int k = 0; k < table.getNumRows(); k++) {

						if (this._Delimiter.equals("")) {
							table.setRow(k);

							int startindex = Integer.parseInt(table.getValue("OFFSET").toString());
							int endindex = Integer.parseInt(table.getValue("LENGTH").toString());
							//System.out.print("Field:"+ table.getValue(0).toString().trim());
							//System.out.print(" OFFSET " + startindex);
							//System.out.println(" LENGTH " + endindex);
							String s = "";
							if(startindex + endindex - 1 == str2.length()){
								s = str2.substring(startindex, startindex + endindex - 1 );
							}else if(startindex + endindex <=str2.length())
							{
								s = str2.substring(startindex, startindex + endindex  );
							}
							//System.out.println(s.trim());
							row.setObject(table.getValue(0).toString().trim(),s.trim());
						} else if (strArray.length > k) {
							table.setRow(k);
							// row[table[k][0].GetValue().ToString().Trim()] =
							// strArray[k].Trim();
							row.setObject(table.getValue(0).toString().trim(),
									strArray[k].trim());
						}
					}
				}
				t.getRows().add(row);
			}
			this._LastPrimaryKey = str;
		}
		this._FetchedRows += f.getTableParameterList().getTable("DATA")
				.getNumRows();
		if (this._RaiseIncomingPackageEvent) {
			// if (this.IncomingPackage != null)
			// {
			// this.IncomingPackage(this, t);
			t.getRows().clear();
			// t.AcceptChanges();

			// }
		}
		// else if (this.PackageProgress != null)
		// {
		// this.PackageProgress(this, t.Rows.Count);
		// }
	}

	/**
	 * 返回一个表或结构的所有字段列表
	 * @return
	 * @throws JCoException
	 */
	public ReadTableFieldCollection GetAllFieldsOfTable() throws JCoException {
		ReadTableFieldCollection fields = new ReadTableFieldCollection();

		if (!(this.getTableName() == null || this.getTableName() == "")) {
			return null;
		}
		
		JCoFunction function = des.getRepository().getFunction("DDIF_FIELDINFO_GET");
		function.getImportParameterList().setValue("LANGU",
				Converts.languageIsotoSap(this.des.getLanguage()));
		function.getImportParameterList().setValue("TABNAME",
				this.getTableName());
		function.execute(des);
		JCoTable DFIES = function.getTableParameterList().getTable("DFIES_TAB");
		for (int k = 0; k < DFIES.getNumRows(); k++) {
			DFIES.setRow(k);
			// IRfcStructure structure = function.GetTable("DFIES_TAB")[k];
			ReadTableField newField = new ReadTableField();
			newField.setFieldName(DFIES.getValue("FIELDNAME").toString());
			newField.setLenght(Integer.parseInt(DFIES.getValue("OUTPUTLEN").toString()));
			newField.setABAPType(DFIES.getValue("INTTYPE").toString());
			newField.setFieldText(DFIES.getValue("FIELDTEXT").toString());
			newField.setDecimals(Integer.parseInt(DFIES.getValue("DECIMALS").toString()));
			newField.setCheckTable(DFIES.getValue("CHECKTABLE").toString());
			// fields.Add(new
			// ReadTableField(structure["FIELDNAME"].GetValue().ToString(),
			// Convert.ToInt32(structure["OUTPUTLEN"].GetValue()),
			// structure["INTTYPE"].GetValue().ToString(),
			// structure["FIELDTEXT"].GetValue().ToString(),
			// Convert.ToInt32(structure["DECIMALS"].GetValue()),structure["CHECKTABLE"].GetValue().ToString()));
			fields.add(newField);
		}
		return fields;

	}

	public void Run() throws Exception {
		JCoFunction function;
		JCoTable table2;				
		this._LastPrimaryKey = "";
		this._FetchedRows = 0;
		if ((this._RowCount == 0) && (this._PackageSize > 0)) {
			this._RowCount = 0x11d260c0;
		}
		this.anzahlaufrufe = 0;
		if (this._FunctionName.equals("")) {
//			boolean isUnicode = this.des.getRepository().isUnicode();
			// if (this.des.Codepage.ToString().Equals(""))
			// {
			// this.des.Repository.UnicodeEnabled = true;
			// }
			if (this._UsePrimaryKeyPackaging) {
				throw new SAPException(
						"Messages.Donotuseprimaykeypackagingwithoutacustomfunctionmodule");
				// throw new
				// Exception("Donot use primaykey packaging without a custom function module");
			}
			// function = new RFCFunction(this.con, "RFC_READ_TABLE");
			function = des.getRepository().getFunction("RFC_READ_TABLE");
			// function.Exports.Add("QUERY_TABLE", RfcDataType.CHAR,
			// this.con.IsUnicode ? 60 : 30);
			// function.Exports.Add("DELIMITER", RfcDataType.CHAR,
			// this.con.IsUnicode ? 2 : 1);
			// function.Exports.Add("ROWSKIPS", RfcDataType.INT, 4);
			// function.Exports.Add("ROWCOUNT", RfcDataType.INT, 4);
			// function.Tables.Add("DATA").Columns.Add("WA", this.con.IsUnicode
			// ? 0x400 : 0x200, RfcDataType.CHAR);
			// table2 = function.Tables.Add("FIELDS");
			// table2.Columns.Add("FIELDNAME", this.con.IsUnicode ? 60 : 30,
			// RfcDataType.CHAR);
			// table2.Columns.Add("OFFSET", this.con.IsUnicode ? 12 : 6,
			// RfcDataType.NUM);
			// table2.Columns.Add("LENGTH", this.con.IsUnicode ? 12 : 6,
			// RfcDataType.NUM);
			// table2.Columns.Add("TYPE", this.con.IsUnicode ? 2 : 1,
			// RfcDataType.CHAR);
			// table2.Columns.Add("FIELDTEXT", this.con.IsUnicode ? 120 : 60,
			// RfcDataType.CHAR);
			// function.Tables.Add("OPTIONS").Columns.Add("TEXT",
			// this.con.IsUnicode ? 0x90 : 0x48, RfcDataType.CHAR);
			// this.con.IsUnicode = isUnicode;
		} else {
			function = this.des.getRepository().getFunction(this._FunctionName);

//			JCoTable table3 = function.getTableParameterList().getTable(
//					"OPTIONS");
			table2 = function.getTableParameterList().getTable("FIELDS");
//			JCoTable table = function.getTableParameterList().getTable("DATA");
		}
		if (this.getUsePrimaryKeyPackaging()) {
			this._PrimaryKeys.clear();
			JCoFunction function2 = des.getRepository().getFunction("DDIF_FIELDINFO_GET");
			// .GenerateFunctionObjectForDDIF_FIELDINFO_GET(this.con.IsUnicode);
			// function2.Connection = this.con;
			function2.getImportParameterList().setValue("TABNAME",this.getTableName());
			// ["TABNAME"].SetValue(this.TableName);
			function2.execute(des);
			JCoTable DFIES = function2.getTableParameterList().getTable(
					"DFIES_TAB");
			for (int i = 0; i < DFIES.getNumRows(); i++) {
				DFIES.setRow(i);
				if (DFIES.getValue("KEYFLAG").toString().equals("X")) {
					ReadTableField newParameter = new ReadTableField();
					newParameter.setFieldName(DFIES.getValue("FIELDNAME")
							.toString());
					newParameter.setLenght(Integer.parseInt(DFIES.getValue(
							"OUTPUTLEN").toString()));
					newParameter.setABAPType("C");
					newParameter.setFieldText("");
					newParameter.setDecimals(0);
					this._PrimaryKeys.add(newParameter);
				}
			}
		}
		if (this.getUsePrimaryKeyPackaging()) {
			for (int j = 0; j < this._PrimaryKeys.size(); j++) {
				table2 = function.getTableParameterList().getTable("FIELDS");
				table2.appendRow();
				table2.setValue("FIELDNAME", this._PrimaryKeys.get(j).getFieldName());
			}
			if (this.fields.size() == 0) {
				this.GetAllFieldsOfTable();
				for (int k = 0; k < this._Fields.size(); k++) {
					this.addField(this._Fields.get(k).getFieldName());
				}
			}
		}
		for (int i = 0; i < this.fields.size(); i++) {
			table2 = function.getTableParameterList().getTable("FIELDS");
			table2.appendRow();
			table2.setValue("FIELDNAME", this.fields.get(i).toString());
		}
		function.getImportParameterList().setValue("QUERY_TABLE",this.getTableName());
		if ((this._PackageSize > 0)
				&& ((this._RowCount > this._PackageSize) || (this._RowCount == 0))) {
			function.getImportParameterList().setValue("ROWCOUNT",
					this._PackageSize);
			function.getImportParameterList().setValue("QUERY_TABLE", 0);
		} else {
			function.getImportParameterList().setValue("ROWCOUNT",
					this._RowCount);
			function.getImportParameterList().setValue("ROWSKIPS",
					this._RowSkip);
		}
		if (this.OHSExtraction) {
			function.getImportParameterList().setValue("ROWCOUNT", 0);
			function.getImportParameterList().setValue("ROWSKIPS", 0);
			this.OHSLastPackageNr = 1;
			function.getImportParameterList().setValue("REQUESTID",
					this.OHSRequestID);
			function.getImportParameterList().setValue("PACKETID",
					this.OHSLastPackageNr);
		}
		this.executeRFC_READ_TABLE(function);
		this.fields.clear();
		this.t = new DataTable();
		// this.t.BeginInit();

		table2 = function.getTableParameterList().getTable("FIELDS");
		if (this.getUsePrimaryKeyPackaging()) {
			this._Fields.clear();
			for (int m = this._PrimaryKeys.size(); m < table2.getNumRows(); m++) {
				table2.setRow(m);

				DataColumn col = new DataColumn();
				col.setColumnName(table2.getValue("FIELDNAME").toString());
				col.setLabel(table2.getValue("FIELDTEXT").toString());
				col.setDataTypeName(table2.getMetaData().getTypeAsString("FIELDNAME"));
				col.setDataTypeName(table2.getMetaData().getTypeAsString("FIELDNAME"));
//				col.setDataTypeName("String");
				this.t.getColumns().add(col);

				ReadTableField field = new ReadTableField();
				field.setFieldName(table2.getValue("FIELDNAME").toString());
				field.setLenght(Integer.parseInt(table2.getValue("LENGTH")
						.toString()));
				field.setABAPType(table2.getValue("TYPE").toString());
				field.setFieldText(table2.getValue("FIELDTEXT").toString());
				field.setDecimals(0);
				this._Fields.add(field);
			}
		} else {
			this._Fields.clear();
			for (int n = 0; n < table2.getNumRows(); n++) {
				table2.setRow(n);
				DataColumn col = new DataColumn();
				col.setColumnName(table2.getValue("FIELDNAME").toString());
				col.setLabel(table2.getValue("FIELDTEXT").toString());
				col.setDataTypeName(table2.getMetaData().getTypeAsString("FIELDNAME"));
//				col.setDataTypeName("String");
				this.t.getColumns().add(col);

				ReadTableField field = new ReadTableField();
				field.setFieldName(table2.getValue("FIELDNAME").toString());
				field.setLenght(Integer.parseInt(table2.getValue("LENGTH")
						.toString()));
				field.setABAPType(table2.getValue("TYPE").toString());
				field.setFieldText(table2.getValue("FIELDTEXT").toString());
				field.setDecimals(0);
				this._Fields.add(field);

			}
		}
		// this.t.EndInit();
		this.ProcessRetrievdData(this.t, function);
		boolean flag2 = false;
		if ((this._PackageSize > 0) && (this._FetchedRows < this._RowCount)) {
			flag2 = true;
		}
		while (flag2) {
			if (this.OHSExtraction) {
				function.getImportParameterList().setValue("ROWCOUNT", 0);
				function.getImportParameterList().setValue("ROWSKIPS", 0);
				this.OHSLastPackageNr++;
				function.getImportParameterList().setValue("REQUESTID",
						this.OHSRequestID);
				function.getImportParameterList().setValue("PACKETID",
						this.OHSLastPackageNr);
			} else {
				function.getImportParameterList().setValue("ROWCOUNT",
						this._PackageSize);
				function.getImportParameterList().setValue("ROWSKIPS",
						this._FetchedRows);

				if ((this._RowCount > 0)
						&& ((this._FetchedRows + this._PackageSize) > this._RowCount)) {
					function.getImportParameterList().setValue("ROWCOUNT",
							this._RowCount - this._FetchedRows);
					// function["ROWCOUNT"].SetValue(this.RowCount -
					// this._FetchedRows);
				}
			}
			function.getTableParameterList().getTable("DATA").clear();
			this.executeRFC_READ_TABLE(function);
			this.ProcessRetrievdData(this.t, function);
			if ((this._FetchedRows >= this._RowCount) && (this._RowCount > 0)) {
				flag2 = false;
			}
			if (function.getTableParameterList().getTable("DATA").getNumRows() < this._PackageSize) {
				flag2 = false;
			}
			if (this.OHSExtraction
					&& (function.getTableParameterList().getTable("DATA")
							.getNumRows() > 0)) {
				flag2 = true;
			}
		}
		// function.Tables["DATA"].Dispose();
	}

	public void SetCustomFunctionName(String FunctionName) {
		this._FunctionName = FunctionName;
	}

	public String getDelimiter() {
		return this._Delimiter;
	}

	public void setDelimiter(String delimiter) {
		this._Delimiter = delimiter;
	}

	public void setTableName(String tableName) {
		this._TableName = tableName;
	}

	public String getTableName() {
		return this._TableName;
	}

	public void setRowCount(int rowCount) {
		this._RowCount = rowCount;
	}

	public void setRowSkip(int rowSkip) {
		this._RowSkip = rowSkip;
	}
	
	public DataTable getResult(){
		return this.t;
	}
	
	public ReadTableFieldCollection getFields(){
		return this._Fields;
	}
	public boolean getUsePrimaryKeyPackaging() {
		return this._UsePrimaryKeyPackaging;
	}

	public void setUsePrimaryKeyPackaging(boolean usePrimaryKeyPackaging) {
		this._UsePrimaryKeyPackaging = usePrimaryKeyPackaging;
	}
}
