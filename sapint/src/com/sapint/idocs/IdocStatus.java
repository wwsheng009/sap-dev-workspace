/**
 * 
 */
package com.sapint.idocs;

import java.sql.Time;
import java.util.Date;

/**
 * @author vincent
 *
 */
public class IdocStatus {
	private Date CreationDateTime = new Date();
	private String Descriptiosn = "";
    private String Status = "";
    private String StatusVar1 = "";
    private String StatusVar2 = "";
    private String StatusVar3 = "";
    private String StatusVar4 = "";
    private String UserName = "";
    
    /**
	 * @return the creationDateTime
	 */
	public Date getCreationDateTime() {
		return CreationDateTime;
	}
	/**
	 * @param creationDateTime the creationDateTime to set
	 */
	public void setCreationDateTime(Date creationDateTime) {
		CreationDateTime = creationDateTime;
	}
	/**
	 * @return the descriptiosn
	 */
	public String getDescriptiosn() {
		return Descriptiosn;
	}
	/**
	 * @param descriptiosn the descriptiosn to set
	 */
	public void setDescriptiosn(String descriptiosn) {
		Descriptiosn = descriptiosn;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}
	/**
	 * @return the statusVar1
	 */
	public String getStatusVar1() {
		return StatusVar1;
	}
	/**
	 * @param statusVar1 the statusVar1 to set
	 */
	public void setStatusVar1(String statusVar1) {
		StatusVar1 = statusVar1;
	}
	/**
	 * @return the statusVar2
	 */
	public String getStatusVar2() {
		return StatusVar2;
	}
	/**
	 * @param statusVar2 the statusVar2 to set
	 */
	public void setStatusVar2(String statusVar2) {
		StatusVar2 = statusVar2;
	}
	/**
	 * @return the statusVar3
	 */
	public String getStatusVar3() {
		return StatusVar3;
	}
	/**
	 * @param statusVar3 the statusVar3 to set
	 */
	public void setStatusVar3(String statusVar3) {
		StatusVar3 = statusVar3;
	}
	/**
	 * @return the statusVar4
	 */
	public String getStatusVar4() {
		return StatusVar4;
	}
	/**
	 * @param statusVar4 the statusVar4 to set
	 */
	public void setStatusVar4(String statusVar4) {
		StatusVar4 = statusVar4;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return UserName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

}
