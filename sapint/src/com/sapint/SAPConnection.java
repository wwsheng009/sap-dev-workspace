/**
 * 
 */
package com.sapint;

import java.util.ArrayList;
import java.util.Hashtable;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import com.sapint.Utils.SAPException;
import com.sapint.idocs.Idoc;
import com.sapint.idocs.IdocSegment;

/**
 * @author vincent
 *
 */
public class SAPConnection {
	private JCoDestination Des;
	private String SysName;
    private boolean Logging;
    private String Client;
    
    
    public Idoc CreateIdoc(String IdocType, String Enhancement)
    {
        return this.InternalCreateIdoc(IdocType, Enhancement, false);
    }
    
    private Idoc InternalCreateIdoc(String IdocType, String Enhancement, boolean CreateEmpty)
    {
        IdocType = IdocType.toUpperCase();
        JCoFunction function = null;
		try {
			function = Des.getRepository().getFunction("IDOCTYPE_READ_COMPLETE");
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        function.getImportParameterList().setValue("PI_IDOCTYP", IdocType);
        function.getImportParameterList().setValue("PI_CIMTYP", Enhancement);

        if (this.Logging)
		{
		 //   function.SaveToXML("IDOCTYPE_READ_COMPLETE_01_" + DateTime.Now.Ticks.ToString() + ".xml");
		 //   function.
		}
		try {
			function.execute(Des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.Logging)
		{
		 //   function.SaveToXML("IDOCTYPE_READ_COMPLETE_02_" + DateTime.Now.Ticks.ToString() + ".xml");
		}
        JCoRecord structure = function.getExportParameterList().getStructure("PE_HEADER");
        Idoc idoc = new Idoc(IdocType, Enhancement);
        idoc.setCon(this);
        idoc.setMANDT(this.Des.getClient());
        idoc.setDescription(structure.getValue("DESCRP").toString());

         
        JCoTable table = function.getTableParameterList().getTable("PT_SEGMENTS");
        Hashtable hashtable = new Hashtable();
        for (int i = 0; i < table.getNumRows(); i++)
        {
        	table.setRow(i);
            if (table.getValue("PARPNO").toString() == "0000")
            {
                IdocSegment newSegment = new IdocSegment();
                if (!CreateEmpty)
                {
                    idoc.getSegments().add(newSegment);
                }
                newSegment.setSegmentName(table.getValue("SEGMENTTYP").toString());
                newSegment.setSegmentType(table.getValue("SEGMENTDEF").toString());
                newSegment.setDescription(table.getValue("DESCRP").toString());
                int num2 = Integer.parseInt(table.getValue("GRP_OCCMAX").toString().substring(5,5));
                newSegment.setMaxOccur((num2 == 0) ? Integer.parseInt(table.getValue("OCCMAX").toString().substring(5, 5)) : num2);
                if (newSegment.getMaxOccur() == 0)
                {
                    newSegment.setMaxOccur(1);
                }
                String key = table.getValue("NR").toString();
                hashtable.put(key, newSegment);
            }
            else
            {
                String str2 = table.getValue("PARPNO").toString();
                String str3 = table.getValue("NR").toString();
                IdocSegment segment2 = (IdocSegment)hashtable.get(str2);
                if (segment2 == null)
                {
                    try {
						throw new SAPException("The idoc structure is not valid");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
                IdocSegment segment3 = new IdocSegment();
                if (!CreateEmpty)
                {
                    segment2.getChildSegments().add(segment3);
                }
                segment3.setSegmentName(table.getValue("SEGMENTTYP").toString());
                segment3.setSegmentType(table.getValue("SEGMENTDEF").toString());
                segment3.setDescription(table.getValue("DESCRP").toString());
                int num3 = Integer.parseInt(table.getValue("GRP_OCCMAX").toString().substring(5,5));
                segment3.setMaxOccur((num3 == 0) ? Integer.parseInt(table.getValue("OCCMAX").toString().substring(5, 5)) : num3);
                if (segment3.getMaxOccur() == 0)
                {
                	segment3.setMaxOccur(1);
                }
                String key = table.getValue("NR").toString();
                 hashtable.put(str3, segment3);
                
            }
        }
        JCoTable table2 = function.getTableParameterList().getTable("PT_FIELDS");
        
        for(int i = 0 ; i<hashtable.size();i++){
        	IdocSegment segment4 = (IdocSegment)hashtable.get(i);
        
            for (int j = 0; j < table2.getNumRows(); j++)
            {
            	table2.setRow(j);
            	
                if (table2.getValue("SEGMENTTYP").toString().trim() == segment4.getSegmentName().trim())
                {
                    segment4.getFields().Add(
                    		table2.getValue("FIELDNAME").toString(), 
                    		table2.getValue("DESCRP").toString(), 
                    		Integer.parseInt(table2.getValue("EXTLEN").toString()), 
                    		Integer.parseInt(table2.getValue("BYTE_FIRST").toString()) - 0x40, 
                    		table2.getValue("DATATYPE").toString(), 
                    		"");
                }
            }
            idoc.StoreSegmentForFurtherUse(segment4.Clone());
        }
        return idoc;
    }
    

    
    public SAPConnection getSAPConnection(){
    	return this;
    }
    
    
    
    
}
