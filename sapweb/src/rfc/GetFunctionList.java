package rfc;
import com.sapint.wsclient.RfcWS;
import com.sapint.wsclient.RfcWSService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class GetFunctionList extends ActionSupport {
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream; 
	}

	private String functionName = "";
	public String getFunctionName(){
		return this.functionName;
	}
	public void setFunctionName(String functionName){
		this.functionName = functionName;
		System.out.println(functionName);
	}
	
	public String execute() throws Exception{
		RfcWS rfcws = new RfcWSService().getRfcWSPort();

		String returnContent = rfcws.getRFCFunctionLIst(functionName);
		System.out.println(functionName);
		System.out.println();

		this.inputStream = new ByteArrayInputStream(returnContent.getBytes("UTF-8"));
		return SUCCESS;
	}
}
