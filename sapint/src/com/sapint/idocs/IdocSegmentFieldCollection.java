/**
 * 
 */
package com.sapint.idocs;

import java.util.ArrayList;

import com.sapint.Utils.SAPException;

/**
 * @author vincent
 *
 */
public class IdocSegmentFieldCollection extends ArrayList<IdocSegmentField>{

	public IdocSegmentField Add(String Name)
    {
        IdocSegmentField field = new IdocSegmentField(Name);
        super.add(field);
        return field;
    }
	public IdocSegmentField Add(String Name, String Description, int Length, int Offset, String DataType, Object FieldValue)
    {
        IdocSegmentField field = new IdocSegmentField(Name, Description, Length, Offset, DataType, FieldValue);
        super.add(field);
        return field;
    }
	
    public  IdocSegmentField get(String SegmentName)
    {

            for (int i = 0; i < this.size(); i++)
            {
                if (((IdocSegmentField) this.get(i)).getFieldName() == SegmentName.toUpperCase().trim())
                {
                    return this.get(i);
                }
            }
            try {
				throw new SAPException(String.format("Messages.CouldnotfindElement_{0}", SegmentName));
			} catch (SAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
     }

    public void add(String SegmentName,IdocSegmentField idocSegmentField){
        {
            for (int i = 0; i < this.size(); i++)
            {
                if (((IdocSegmentField) this.get(i)).getFieldName() == SegmentName.toUpperCase().trim())
                {
                    this.set(i, idocSegmentField);
                }
            }
            try {
				throw new SAPException(String.format("Messages.CouldnotfindElement_{0}", SegmentName));
			} catch (SAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
