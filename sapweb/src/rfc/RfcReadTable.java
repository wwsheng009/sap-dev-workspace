package rfc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;
import com.sapint.wsclient.RfcReadTableWSService;
import com.sapint.wsclient.RfcWS;
import com.sapint.wsclient.RfcWSService;

public class RfcReadTable extends ActionSupport {

	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream; 
	}
	private String tableName = "";

	public void setTableName(String tableName){
		this.tableName = tableName;
		System.out.println(tableName);
	}
	
	private String fields = "";
	public void setFields(String fields){
		this.fields = fields;
	}
	private String sql = "";
	public void setSql(String sql){
		this.sql = sql;
	}
	
	private int rowCount = 100;
	public void setRowCount(int rowCount){
		this.rowCount = rowCount;
	}
	public String execute() throws Exception{
		
		com.sapint.wsclient.RfcReadTableWS readtablews = new RfcReadTableWSService().getRfcReadTableWSPort();
		readtablews.addField(fields);
		readtablews.addCriteria(sql);
		String returnContent = readtablews.getDataTable(tableName, this.rowCount,0);
		System.out.println(returnContent);
		this.inputStream = new ByteArrayInputStream(returnContent.getBytes("UTF-8"));
		return SUCCESS;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
