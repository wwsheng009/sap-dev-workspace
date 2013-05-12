package com.sapint.Utils;

public class ReadTableField {
	private String _ABAPType;
	private int _Decimals;
	private String _FieldName;
	private String _FieldText;
	private String _CheckTable;
	private int _Length;
	private boolean _Active;
	protected String LastKeyValue;
	
	public ReadTableField(){
		this._FieldName="";
		this._ABAPType="";
		this._FieldText="";
		this.LastKeyValue="";
	}
	
	public ReadTableField(String FieldName,int Length,String ABAPType,String FieldText,int Decimals){
		this._FieldName = "";
		this._ABAPType = "";
		this._FieldText = "";
		this.LastKeyValue = "";
		this._FieldName = FieldName;
		this._Length = Length;
		this._ABAPType = ABAPType;
		this._FieldText = FieldText;
		this._Decimals = Decimals;
	}
    public ReadTableField(String FieldName, int Length, String ABAPType, String FieldText, int Decimals,String CheckTable)
    {
        this._FieldName = "";
        this._ABAPType = "";
        this._FieldText = "";
        this.LastKeyValue = "";
        this._CheckTable = "";
        this._FieldName = FieldName;
        this._Length = Length;
        this._ABAPType = ABAPType;
        this._FieldText = FieldText;
        this._Decimals = Decimals;
        this._CheckTable = CheckTable;
    }
    
    public String getABAPType(){
    	return this._ABAPType;
    }
    public void setABAPType(String abapType){
    	this._ABAPType = abapType;
    }
    
    public int getDecimals(){
    	return this._Decimals;
    }
    public void setDecimals(int decimals){
    	this._Decimals = decimals;
    }
    
    public String getFieldName(){
    	return this._FieldName;
    }
    public void setFieldName(String fieldName){
    	this._FieldText = fieldName;
    }
    
    public String getFieldText(){
    	return this._FieldText;
    }
    public void setFieldText(String fieldText){
    	this._FieldText = fieldText;
    }
    public int getLength(){
    	return this._Length;
    }
    public void setLenght(int length){
    	this._Length = length;
    }
    public String getCheckTable(){
    	return this._CheckTable;
    }
    public void setCheckTable(String checkTable){
    	this._CheckTable = checkTable;
    }
    public boolean getActive(){
    	return this._Active;
    }
    public void setActive (boolean active){
    	this._Active = active;
    }
}
