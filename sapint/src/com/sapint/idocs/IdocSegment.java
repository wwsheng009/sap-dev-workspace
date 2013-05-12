/**
 * 
 */
package com.sapint.idocs;

import org.apache.commons.lang.StringUtils;

import com.sapint.Utils.SAPException;

/**
 * @author vincent
 *
 */
public class IdocSegment {
	private IdocSegmentCollection ChildSegments = new IdocSegmentCollection();
    private String DataBuffer = String.format("%-10s", " ");
    private String Description = "";
    private IdocSegmentFieldCollection Fields = new IdocSegmentFieldCollection();
    private int MaxOccur = 1;
    private String SegmentName = "";
    private String SegmentType = "";
    protected String SegmentNumberForPlainLoad = "";
    
    
    public IdocSegment Clone()
    {
        IdocSegment segment = new IdocSegment();
        segment.DataBuffer = this.DataBuffer;
        segment.Description = this.Description;
        segment.SegmentName = this.SegmentName;
        segment.SegmentType = this.SegmentType;
        segment.MaxOccur = this.MaxOccur;
        
        for (int i = 0; i < this.Fields.size(); i++)
        {
            IdocSegmentField newParameter = this.Fields.get(i);
            segment.Fields.add(newParameter);
        }
        return segment;
    }
    
    public String ReadDataBuffer(int Offset, int Length)
    {
        if (((Offset < 0) || (Offset > 0x3e8)) || ((Offset + Length) > 0x3e8))
        {
            try {
				throw new SAPException("Messages.TheIdocdatabufferswidthis1000bytes");
			} catch (SAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        for (int i = 0; i < this.Fields.size(); i++)
        {
            IdocSegmentField field = this.Fields.get(i);
            String content = "";
            if (field.getFieldName() != null)
            {
                content = field.getFieldValue().toString();
            }
            if (field.getDataType() == "NUMC")
            {
//                content = content.PadLeft(field.getExternalLength(), "0");
                content = StringUtils.leftPad(content,field.getExternalLength(),"0");
            }
            this.WriteDataBuffer(content, field.getOffsetInBuffer(), field.getExternalLength());
        }
        return this.DataBuffer.substring(Offset, Length);
    }
    
    @Override
    public String toString()
    {
        return ((this.Description + " (" + this.SegmentName + ") \r\n") + this.DataBuffer + "\r\n");
    }
    
    public void WriteDataBuffer(String Content, int Offset, int Length)
    {
        if (((Offset < 0) || (Offset > 0x3e8)) || ((Offset + Length) > 0x3e8))
        {
            try {
				throw new SAPException("Messages.TheIdocdatabufferswidthis1000bytes");
			} catch (SAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//        Content = Content.PadRight(0x3e8, " ".ToCharArray()[0]).Substring(0, Length);
        Content = StringUtils.rightPad(Content, 0x3e8, " ").substring(0,Length);
        		
        this.DataBuffer = this.DataBuffer.substring(0, Offset) + Content + this.DataBuffer.substring(Offset + Length, (0x3e8 - Length) - Offset);
    }
    
    
    
    
    
    
	/**
	 * @return the childSegments
	 */
	public IdocSegmentCollection getChildSegments() {
		return ChildSegments;
	}
	/**
	 * @param childSegments the childSegments to set
	 */
	public void setChildSegments(IdocSegmentCollection childSegments) {
		ChildSegments = childSegments;
	}
	/**
	 * @return the dataBuffer
	 */
	public String getDataBuffer() {
		return DataBuffer;
	}
	/**
	 * @param dataBuffer the dataBuffer to set
	 */
	public void setDataBuffer(String dataBuffer) {
		DataBuffer = dataBuffer;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * @return the fields
	 */
	public IdocSegmentFieldCollection getFields() {
		return Fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(IdocSegmentFieldCollection fields) {
		Fields = fields;
	}
	/**
	 * @return the maxOccur
	 */
	public int getMaxOccur() {
		return MaxOccur;
	}
	/**
	 * @param maxOccur the maxOccur to set
	 */
	public void setMaxOccur(int maxOccur) {
		MaxOccur = maxOccur;
	}
	/**
	 * @return the segmentName
	 */
	public String getSegmentName() {
		return SegmentName;
	}
	/**
	 * @param segmentName the segmentName to set
	 */
	public void setSegmentName(String segmentName) {
		SegmentName = segmentName;
	}
	/**
	 * @return the segmentType
	 */
	public String getSegmentType() {
		return SegmentType;
	}
	/**
	 * @param segmentType the segmentType to set
	 */
	public void setSegmentType(String segmentType) {
		SegmentType = segmentType;
	}
    
	public boolean HasChildren(){
		return (this.ChildSegments.size() > 0);
	}
    
}
