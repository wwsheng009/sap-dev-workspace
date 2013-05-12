package com.sapint;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

public class BussinessObjectMethod {
	private String Name;
	private String MethodName;
	private String ObjectName;
	private BapiReturnCollection Returns;
	private JCoDestination des;
	
	public BussinessObjectMethod(String sysName){
		this.ObjectName = "";
		this.MethodName = "";
		this.Returns = new BapiReturnCollection();
		try {
			this.des = JCoDestinationManager.getDestination(sysName);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void CommitWork(boolean wait){
		JCoFunction function = null;
		try {
			function = des.getRepository().getFunction("BAPI_TRANSACTION_COMMIT");
			if (function == null){
				 throw new RuntimeException("BAPI_TRANSACTION_COMMIT not found in SAP.");
			}
			
			if(wait){
				function.getImportParameterList().setValue("WAIT", "X");
			}
			function.execute(des);
			
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void RollbackWork(){
		JCoFunction function = null;
		try {
			 function = des.getRepository().getFunction("BAPI_TRANSACTION_ROLLBACK");
			 if (function == null){
				 throw new RuntimeException("BAPI_TRANSACTION_COMMIT not found in SAP.");
			}
			 
			 function.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void Excute(){
		this.Returns.clear();
		
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return MethodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return ObjectName;
	}
	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}
	/**
	 * @return the returns
	 */
	public BapiReturnCollection getReturns() {
		return Returns;
	}
	/**
	 * @param returns the returns to set
	 */
	public void setReturns(BapiReturnCollection returns) {
		Returns = returns;
	}
	/**
	 * @return the des
	 */
	public JCoDestination getDes() {
		return des;
	}
	/**
	 * @param des the des to set
	 */
	public void setDes(JCoDestination des) {
		this.des = des;
	}

}
