/**
 * 
 */
package com.sapint.idocs;

/**
 * @author vincent
 * 
 */
public class IdocSegmentField {
	private String DataType;
	private String Description;
	private int ExternalLength;
	private String FieldName;
	private Object FieldValue;
	private int OffsetInBuffer;

	public IdocSegmentField() {
	}

	public IdocSegmentField(String Name) {
		this.FieldName = Name.toUpperCase();
	}

	public IdocSegmentField(String Name, String Description, int Length, int Offset, String DataType, Object FieldValue) {
		this.FieldName = Name.toUpperCase().trim();
		this.Description = Description.trim();
		this.ExternalLength = Length;
		this.OffsetInBuffer = Offset;
		this.FieldValue = FieldValue;
		this.DataType = DataType.trim();
	}

	public IdocSegmentField Clone() {
		IdocSegmentField idocSegmentfield = new IdocSegmentField();
		idocSegmentfield.DataType = this.DataType;
		idocSegmentfield.Description = this.Description;
		idocSegmentfield.ExternalLength = this.ExternalLength;
		idocSegmentfield.FieldName = this.FieldName;
		idocSegmentfield.FieldValue = this.FieldValue;
		idocSegmentfield.OffsetInBuffer = this.OffsetInBuffer;
		return idocSegmentfield;

	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return DataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(String dataType) {
		DataType = dataType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the externalLength
	 */
	public int getExternalLength() {
		return ExternalLength;
	}

	/**
	 * @param externalLength
	 *            the externalLength to set
	 */
	public void setExternalLength(int externalLength) {
		ExternalLength = externalLength;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return FieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public Object getFieldValue() {
		return FieldValue;
	}

	/**
	 * @param fieldValue
	 *            the fieldValue to set
	 */
	public void setFieldValue(Object fieldValue) {
		FieldValue = fieldValue;
	}

	/**
	 * @return the offsetInBuffer
	 */
	public int getOffsetInBuffer() {
		return OffsetInBuffer;
	}

	/**
	 * @param offsetInBuffer
	 *            the offsetInBuffer to set
	 */
	public void setOffsetInBuffer(int offsetInBuffer) {
		OffsetInBuffer = offsetInBuffer;
	}

}
