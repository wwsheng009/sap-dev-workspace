/**
 * 
 */
package com.sapint.idocs;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import com.sapint.Converts;
import com.sapint.MyStringUtils;
import com.sapint.SAPConnection;
import com.sapint.Utils.SAPException;

/**
 * @author vincent
 * 
 */
public class Idoc {

	private String ARCKEY;
	private IdocSegmentCollection ChildSegments;
	private String CIMTYP;
	private String CREDAT;
	private String CRETIM;
	private String Description;
	private String DIRECT;
	private String DOCNUM;
	private String DOCREL;
	private String EXPRSS;
	private String IDOCTYP;
	private String LastTID;
	private String MANDT;
	private String MESCOD;
	private String MESFCT;
	private String MESTYP;
	private String OUTMOD;
	private String RCVLAD;
	private String RCVPFC;
	private String RCVPOR;
	private String RCVPRN;
	private String RCVPRT;
	private String RCVSAD;
	private String REFGRP;
	private String REFINT;
	private String REFMES;
	private String SERIAL;
	private boolean SkipHLevel;
	private String SNDLAD;
	private String SNDPFC;
	private String SNDPOR;
	private String SNDPRN;
	private String SNDPRT;
	private String SNDSAD;
	private String STATUS;
	private String STD;
	private String STDMES;
	private String STDVRS;
	private String TABNAM;
	private String TEST;
	private JCoDestination des;
	private SAPConnection con;
	private Hashtable PlainLoadSegmentHash;
	private static Dictionary<String, String> plainTextEscapes;
	private static Dictionary<String, String> plainTextUnescapes;
	private int SegNumForPlainFile;
	private Hashtable StoredSegments;

	static {
		Dictionary<String, String> dictionary = new Hashtable<String, String>();
		dictionary.put("\r", "r");
		dictionary.put("\n", "n");
		plainTextEscapes = dictionary;
		Dictionary<String, String> dictionary2 = new Hashtable<String, String>();
		dictionary2.put("r", "\r");
		dictionary2.put("n", "\n");
		plainTextUnescapes = dictionary2;
	}

	public Idoc() {
		this.ChildSegments = new IdocSegmentCollection();
		this.LastTID = "";
		this.TABNAM = "";
		this.MANDT = "";
		this.DOCNUM = "";
		this.DOCREL = "";
		this.STATUS = "";
		this.DIRECT = "";
		this.OUTMOD = "";
		this.EXPRSS = "";
		this.TEST = "";
		this.IDOCTYP = "";
		this.CIMTYP = "";
		this.MESTYP = "";
		this.MESCOD = "";
		this.MESFCT = "";
		this.STD = "";
		this.STDVRS = "";
		this.STDMES = "";
		this.SNDPOR = "";
		this.SNDPRT = "";
		this.SNDPFC = "";
		this.SNDPRN = "";
		this.SNDSAD = "";
		this.SNDLAD = "";
		this.RCVPOR = "";
		this.RCVPRT = "";
		this.RCVPFC = "";
		this.RCVPRN = "";
		this.RCVSAD = "";
		this.RCVLAD = "";
		this.CREDAT = "";
		this.CRETIM = "";
		this.REFINT = "";
		this.REFGRP = "";
		this.REFMES = "";
		this.ARCKEY = "";
		this.SERIAL = "";
		this.Description = "";
		this.StoredSegments = new Hashtable();
		this.TABNAM = "EDIDC40";
	}

	public Idoc(String IdocType, String Extension) {
		this.ChildSegments = new IdocSegmentCollection();
		this.LastTID = "";
		this.TABNAM = "";
		this.MANDT = "";
		this.DOCNUM = "";
		this.DOCREL = "";
		this.STATUS = "";
		this.DIRECT = "";
		this.OUTMOD = "";
		this.EXPRSS = "";
		this.TEST = "";
		this.IDOCTYP = "";
		this.CIMTYP = "";
		this.MESTYP = "";
		this.MESCOD = "";
		this.MESFCT = "";
		this.STD = "";
		this.STDVRS = "";
		this.STDMES = "";
		this.SNDPOR = "";
		this.SNDPRT = "";
		this.SNDPFC = "";
		this.SNDPRN = "";
		this.SNDSAD = "";
		this.SNDLAD = "";
		this.RCVPOR = "";
		this.RCVPRT = "";
		this.RCVPFC = "";
		this.RCVPRN = "";
		this.RCVSAD = "";
		this.RCVLAD = "";
		this.CREDAT = "";
		this.CRETIM = "";
		this.REFINT = "";
		this.REFGRP = "";
		this.REFMES = "";
		this.ARCKEY = "";
		this.SERIAL = "";
		this.Description = "";
		this.StoredSegments = new Hashtable();
		this.IDOCTYP = IdocType.toUpperCase();
		this.CIMTYP = Extension.toUpperCase();
		this.TABNAM = "EDIDC40";
	}

	private void AppendEDIDC40(StringBuilder st, String IdocType) {
		st.append("<xsd:element name=\"EDI_DC40\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>IDoc Control Record for Interface to External System</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:complexType>");
		st.append("<xsd:sequence>");
		st.append("<xsd:element name=\"TABNAM\" type=\"xsd:string\" fixed=\"EDI_DC40\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Name of table structure</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"MANDT\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Client</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"3\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"DOCNUM\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>IDoc number</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"16\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"DOCREL\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>SAP Release for IDoc</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"4\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"STATUS\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Status of IDoc</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"2\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"DIRECT\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Direction</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:enumeration value=\"1\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Outbound</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("</xsd:enumeration>");
		st.append("<xsd:enumeration value=\"2\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Inbound</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("</xsd:enumeration>");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"OUTMOD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Output mode</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"1\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"EXPRSS\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Overriding in inbound processing</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"1\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"TEST\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Test flag</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"1\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"IDOCTYP\" type=\"xsd:string\" fixed=\"" + IdocType + "\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Name of basic type</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"CIMTYP\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Extension (defined by customer)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"30\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"MESTYP\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Message type</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"30\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"MESCOD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Message code</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"3\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"MESFCT\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Message function</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"3\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"STD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>EDI standard, flag</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"1\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"STDVRS\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>EDI standard, version and release</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"6\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"STDMES\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>EDI message type</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"6\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDPOR\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Sender port (SAP System, external subsystem)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"10\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDPRT\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner type of sender</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"2\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDPFC\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner Function of Sender</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"2\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDPRN\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner Number of Sender</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"10\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDSAD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Sender address (SADR)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"21\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SNDLAD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Logical address of sender</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"70\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVPOR\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Receiver port</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"10\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVPRT\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner Type of receiver</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"2\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVPFC\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner function of recipient</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"2\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVPRN\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Partner number of recipient</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"10\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVSAD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Recipient address (SADR)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"21\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"RCVLAD\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Logical address of recipient</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"70\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"CREDAT\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Created on</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"8\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"CRETIM\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Time Created</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"6\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"REFINT\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Transmission file (EDI Interchange)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"14\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"REFGRP\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Message group (EDI Message Group)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"14\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"REFMES\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Message (EDI Message)</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"14\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"ARCKEY\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Key for external message archive</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"70\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("<xsd:element name=\"SERIAL\" minOccurs=\"0\">");
		st.append("<xsd:annotation>");
		st.append("<xsd:documentation>Serialization</xsd:documentation> ");
		st.append("</xsd:annotation>");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:maxLength value=\"20\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:element>");
		st.append("</xsd:sequence>");
		st.append("<xsd:attribute name=\"SEGMENT\" use=\"required\">");
		st.append("<xsd:simpleType>");
		st.append("<xsd:restriction base=\"xsd:string\">");
		st.append("<xsd:enumeration value=\"1\" /> ");
		st.append("</xsd:restriction>");
		st.append("</xsd:simpleType>");
		st.append("</xsd:attribute>");
		st.append("</xsd:complexType>");
		st.append("</xsd:element>");
	}

	public IdocSegment CreateSegment(String SegmentName) {
		SegmentName = SegmentName.trim();
		if (SegmentName.length() < 3) {
			// throw new
			// ERPException(string.Format(Messages.Segment_0_notvalidforthisIdoc,
			// SegmentName));
			try {
				throw new SAPException(String.format("{0}not valid for this Idoc", SegmentName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IdocSegment segment = null;
		segment = (IdocSegment) this.StoredSegments.get(SegmentName);
		if (segment == null) {
			String str = SegmentName.substring(0, SegmentName.length() - 3);
			segment = (IdocSegment) this.StoredSegments.get(str);
			if (segment == null) {
				if (SegmentName.substring(1, 1) == "2") {
					str = SegmentName.substring(0, 1) + "1" + SegmentName.substring(2);
				} else {
					str = SegmentName.substring(0, 1) + "2" + SegmentName.substring(2);
				}
				segment = (IdocSegment) this.StoredSegments.get(str);
				if (segment == null) {
					str = str.substring(0, str.length() - 3);
					segment = (IdocSegment) this.StoredSegments.get(str);
					if (segment == null) {
						segment = new IdocSegment();
					}
				}
			}
		}
		segment.setSegmentName(SegmentName);
		return segment.Clone();
	}

	public IdocStatus GetCurrentStatus() throws SAPException {
		if (this.des == null) {
			throw new SAPException("Messages.NoConnectionAssigned");
			// throw new Exception("NoconnectionAssigned");
		}
		if (this.DOCNUM.trim().equals("")) {
			throw new SAPException("Messages.NoIdocnumbergiven");
			// throw new Exception("No Idoc number given");
		}
		// RFCFunction function =
		// RFCFunctionFactory.GenerateFunctionObjectForEDI_DOCUMENT_OPEN_FOR_READ(this.con.IsUnicode);
		JCoFunction function = null;
		try {
			function = des.getRepository().getFunction("EDI_DOCUMENT_OPEN_FOR_READ");
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// function.Connection = this.con;
		// function["DOCUMENT_NUMBER"].SetValue(this._DOCNUM);
		function.getImportParameterList().setValue("", DOCNUM);
		// function.Invoke(des);
		try {
			function.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// RFCFunction function2 =
		// RFCFunctionFactory.GenerateFunctionObjectForEDI_DOCUMENT_READ_LAST_STATUS(this.con.IsUnicode);
		JCoFunction function2 = null;
		try {
			function2 = des.getRepository().getFunction("EDI_DOCUMENT_READ_LAST_STATUS");
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// function2.Connection = this.con;
		// function2["DOCUMENT_NUMBER"].SetValue(this._DOCNUM);
		function2.getImportParameterList().setValue("", DOCNUM);
		try {
			function2.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JCoRecord structure = function2.getExportParameterList().getStructure("STATUS");
		// IRfcStructure structure = function2.GetStructure("STATUS");
		IdocStatus status = new IdocStatus();
		status.setUserName(structure.getValue("UNAME").toString());// =
																	// structure["UNAME"].GetValue().ToString();
		status.setStatus(structure.getValue("STATUS").toString());// Status =
																	// structure["STATUS"].GetValue().ToString();
		status.setDescriptiosn(Converts.FormatMessage(structure.getValue("STATXT").toString(), structure.getValue("STAPA1").toString(), structure.getValue("STAPA2").toString(),
				structure.getValue("STAPA3").toString(), structure.getValue("STAPA4").toString()));
		// Description = Converts.FormatMessage(structure["STATXT"].ToString(),
		// structure["STAPA1"].ToString(), structure["STAPA2"].ToString(),
		// structure["STAPA3"].ToString(), structure["STAPA4"].ToString());
		status.setStatusVar1(structure.getValue("StatusVar1").toString());
		status.setStatusVar2(structure.getValue("StatusVar2").toString());
		status.setStatusVar3(structure.getValue("StatusVar3").toString());
		status.setStatusVar4(structure.getValue("StatusVar4").toString());

		// DateTime time = new
		// DateTime(Convert.ToInt32(structure["CREDAT"].ToString().Substring(0,
		// 4)), Convert.ToInt32(structure["CREDAT"].ToString().Substring(4, 2)),
		// Convert.ToInt32(structure["CREDAT"].ToString().Substring(6, 2)),
		// Convert.ToInt32(structure["CRETIM"].ToString().Substring(0, 2)),
		// Convert.ToInt32(structure["CRETIM"].ToString().Substring(2, 2)),
		// Convert.ToInt32(structure["CRETIM"].ToString().Substring(4, 2)));

		Date time = new Date();
		time.setYear(Integer.parseInt(structure.getValue("CREDAT").toString().substring(0, 4)));
		time.setMonth(Integer.parseInt(structure.getValue("CREDAT").toString().substring(4, 2)));
		time.setDate(Integer.parseInt(structure.getValue("CREDAT").toString().substring(6, 2)));
		time.setHours(Integer.parseInt(structure.getValue("CRETIM").toString().substring(0, 2)));
		time.setMinutes(Integer.parseInt(structure.getValue("CRETIM").toString().substring(2, 2)));
		time.setSeconds(Integer.parseInt(structure.getValue("CRETIM").toString().substring(4, 2)));
		status.setCreationDateTime(time);
		// RFCFunction function3 =
		// RFCFunctionFactory.GenerateFunctionObjectForEDI_DOCUMENT_CLOSE_READ(this.con.IsUnicode);
		JCoFunction function3 = null;
		try {
			function3 = des.getRepository().getFunction("EDI_DOCUMENT_CLOSE_READ");
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// function3.Connection = this.con;
		function3.getImportParameterList().setValue("DOCUMENT_NUMBER", DOCNUM);
		try {
			function3.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	private String GetIdocSchema() throws SAPException {
		String str = null;
		try {
			Idoc idoc = this.con.CreateIdoc(this.IDOCTYP, this.CIMTYP);
			StringBuilder st = new StringBuilder("");
			st.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
			st.append("<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:msprop=\"urn:schemas-microsoft-com:xml-msprop\" version=\"1.0\">\n");
			st.append("<xsd:element name=\"" + Converts.OriginalNameToXmlName(this.IDOCTYP) + "\">\n");
			st.append("<xsd:annotation>\n");
			st.append("<xsd:documentation>" + idoc.Description.replace("&", StringUtils.EMPTY) + "</xsd:documentation> \n");
			st.append("</xsd:annotation>\n");
			st.append("<xsd:complexType>\n");
			st.append("<xsd:sequence>\n");
			st.append("<xsd:element name=\"IDOC\">\n");
			st.append("<xsd:complexType>\n");
			st.append("<xsd:sequence>\n");
			this.AppendEDIDC40(st, this.IDOCTYP);
			for (IdocSegment segment : idoc.ChildSegments) {
				this.WriteSegment(st, segment);
			}
			st.append("</xsd:sequence>\n");
			st.append("<xsd:attribute name=\"BEGIN\" use=\"required\">\n");
			st.append("<xsd:simpleType>\n");
			st.append("<xsd:restriction base=\"xsd:string\">\n");
			st.append("<xsd:enumeration value=\"1\" />\n ");
			st.append("</xsd:restriction>\n");
			st.append("</xsd:simpleType>\n");
			st.append("</xsd:attribute>\n");
			st.append("</xsd:complexType>\n");
			st.append("</xsd:element>\n");
			st.append("</xsd:sequence>\n");
			st.append("</xsd:complexType>\n");
			st.append("</xsd:element>\n");
			st.append("</xsd:schema>\n");
			str = st.toString();
		} catch (Exception exception) {
				throw new SAPException("Error during IDoc schema generation", exception);

		}
		return str;
	}

	private IdocSegment GetSegmentFromNode(Element node) {
		IdocSegment segment;
		String segmentName = Converts.XmlNameToOriginalName(node.getName());
		try {
			segment = this.CreateSegment(segmentName);
		} finally {
			segment = new IdocSegment();
			segment.setSegmentName(segmentName);
		}
		for (int i = 0; i < node.elements().size(); i++) {
			Element node2 = (Element) node.elements().get(i);
			Element e = (Element) node2.elements().get(0);
			if (node2.getName().equals("BUFFER")) {
				segment.WriteDataBuffer(node2.getText(), 0, 0x3e8);
				segment.getFields().clear();
				this.RegenerateSegment(segment);
			} else if ((node2.elements().size() > 0) && e.getName().equals("BUFFER")) {
				segment.getChildSegments().add(this.GetSegmentFromNode(node2));
			} else {
				String str2 = Converts.XmlNameToOriginalName(node2.getName());
				if (this.IsSegmentValid(str2)) {
					segment.getChildSegments().add(this.GetSegmentFromNode(node2));
				} else {
					try {
						// segment.getFields().get[str2].FieldValue =
						// node2.getText();
						segment.getFields().get(str2).setFieldValue(node2.getText());
					} catch (Exception e1) {
						// segment.WriteDataBuffer((node2.getText().PadRight(0x3e8),
						// 0, 0x3e8);
						segment.WriteDataBuffer(StringUtils.rightPad(node2.getText(), 0x3e8, ""), 0, 0x3e8);
					}
				}
			}
		}
		return segment;
	}

	private Document GetXMLDocument(String StrEncoding) {
		String str = Converts.OriginalNameToXmlName(this.MESTYP);
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"" + StrEncoding + "\" ?>");
		if (!(str == null || str.equals(""))) {
			builder.append("<" + str + ">");
		}
		builder.append("<IDOC>");
		builder.append("<EDI_DC40>");
		builder.append("<TABNAM>" + MyStringUtils.trimEnd(this.TABNAM, "0") + "</TABNAM>");
		builder.append("<MANDT>" + MyStringUtils.trimEnd(this.MANDT, "0") + "</MANDT>");
		builder.append("<DOCNUM>" + MyStringUtils.trimEnd(this.DOCNUM, "0") + "</DOCNUM>");
		builder.append("<DOCREL>" + MyStringUtils.trimEnd(this.DOCREL, "0") + "</DOCREL>");
		builder.append("<STATUS>" + MyStringUtils.trimEnd(this.STATUS, "0") + "</STATUS>");
		builder.append("<DIRECT>" + MyStringUtils.trimEnd(this.DIRECT, "0") + "</DIRECT>");
		builder.append("<OUTMOD>" + MyStringUtils.trimEnd(this.OUTMOD, "0") + "</OUTMOD>");
		builder.append("<EXPRSS>" + MyStringUtils.trimEnd(this.EXPRSS, "0") + "</EXPRSS>");
		builder.append("<IDOCTYP>" + MyStringUtils.trimEnd(this.IDOCTYP, "0") + "</IDOCTYP>");
		builder.append("<CIMTYP>" + MyStringUtils.trimEnd(this.CIMTYP, "0") + "</CIMTYP>");
		builder.append("<MESTYP>" + MyStringUtils.trimEnd(this.MESTYP, "0") + "</MESTYP>");
		builder.append("<MESCOD>" + MyStringUtils.trimEnd(this.MESCOD, "0") + "</MESCOD>");
		builder.append("<MESFCT>" + MyStringUtils.trimEnd(this.MESFCT, "0") + "</MESFCT>");
		builder.append("<STD>" + MyStringUtils.trimEnd(this.STD, "0") + "</STD>");
		builder.append("<STDVRS>" + MyStringUtils.trimEnd(this.STDVRS, "0") + "</STDVRS>");
		builder.append("<STDMES>" + MyStringUtils.trimEnd(this.STDMES, "0") + "</STDMES>");
		builder.append("<SNDPOR>" + MyStringUtils.trimEnd(this.SNDPOR, "0") + "</SNDPOR>");
		builder.append("<SNDPRT>" + MyStringUtils.trimEnd(this.SNDPRT, "0") + "</SNDPRT>");
		builder.append("<SNDPFC>" + MyStringUtils.trimEnd(this.SNDPFC, "0") + "</SNDPFC>");
		builder.append("<SNDPRN>" + MyStringUtils.trimEnd(this.SNDPRN, "0") + "</SNDPRN>");
		builder.append("<SNDSAD>" + MyStringUtils.trimEnd(this.SNDSAD, "0") + "</SNDSAD>");
		builder.append("<SNDLAD>" + MyStringUtils.trimEnd(this.SNDLAD, "0") + "</SNDLAD>");
		builder.append("<RCVPOR>" + MyStringUtils.trimEnd(this.RCVPOR, "0") + "</RCVPOR>");
		builder.append("<RCVPRT>" + MyStringUtils.trimEnd(this.RCVPRT, "0") + "</RCVPRT>");
		builder.append("<RCVPFC>" + MyStringUtils.trimEnd(this.RCVPFC, "0") + "</RCVPFC>");
		builder.append("<RCVPRN>" + MyStringUtils.trimEnd(this.RCVPRN, "0") + "</RCVPRN>");
		builder.append("<RCVSAD>" + MyStringUtils.trimEnd(this.RCVSAD, "0") + "</RCVSAD>");
		builder.append("<RCVLAD>" + MyStringUtils.trimEnd(this.RCVLAD, "0") + "</RCVLAD>");
		builder.append("<CREDAT>" + MyStringUtils.trimEnd(this.CREDAT, "0") + "</CREDAT>");
		builder.append("<CRETIM>" + MyStringUtils.trimEnd(this.CRETIM, "0") + "</CRETIM>");
		builder.append("<REFINT>" + MyStringUtils.trimEnd(this.REFINT, "0") + "</REFINT>");
		builder.append("<REFGRP>" + MyStringUtils.trimEnd(this.REFGRP, "0") + "</REFGRP>");
		builder.append("<REFMES>" + MyStringUtils.trimEnd(this.REFMES, "0") + "</REFMES>");
		builder.append("<ARCKEY>" + MyStringUtils.trimEnd(this.ARCKEY, "0") + "</ARCKEY>");
		builder.append("<SERIAL>" + MyStringUtils.trimEnd(this.SERIAL, "0") + "</SERIAL>");
		builder.append("</EDI_DC40>");
		builder.append("</IDOC>");
		if (MyStringUtils.isNotEmpty(str)) {
			builder.append("</" + str + ">");
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(str);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// doc.(builder.toString());
		Element element = (Element) doc.node(0);
		for (int i = 0; i < this.getSegments().size(); i++) {

			element.add((this.GetXMLNodeFromSegment(this.getSegments().get(i), doc)));
		}
		return doc;
	}

	private Element GetXMLNodeFromSegment(IdocSegment seg, Document doc) {
		Element node = doc.addElement(Converts.OriginalNameToXmlName(seg.getSegmentName()));
		if (seg.getFields().size() > 0) {
			for (IdocSegmentField field : seg.getFields()) {
				if (!field.getFieldValue().toString().trim().equals("")) {
					Element newChild = node.addElement(Converts.OriginalNameToXmlName(field.getFieldName()));
					newChild.setText(MyStringUtils.trimEnd(field.getFieldValue().toString(), "0"));
				}
			}
		} else {
			Element node3 = doc.addElement("BUFFER");
			node3.setText(MyStringUtils.trimEnd(seg.ReadDataBuffer(0, 0x3e8)));
		}

		for (IdocSegment segment : seg.getChildSegments()) {
			node.add(this.GetXMLNodeFromSegment(segment, doc));
		}
		return node;
		// XmlNode node = doc.CreateNode(XmlNodeType.Element,
		// Converts.OriginalNameToXmlName(seg.SegmentName), "");
		// if (seg.Fields.Count > 0)
		// {
		// foreach (IdocSegmentField field in seg.Fields)
		// {
		// if (!field.FieldValue.ToString().Trim().Equals(""))
		// {
		// XmlNode newChild = doc.CreateNode(XmlNodeType.Element,
		// Converts.OriginalNameToXmlName(field.FieldName), "");
		// newChild.InnerText = field.FieldValue.ToString().TrimEnd(new
		// char[0]);
		// node.AppendChild(newChild);
		// }
		// }
		// }
		// else
		// {
		// XmlNode node3 = doc.CreateNode(XmlNodeType.Element, "BUFFER", "");
		// node3.InnerText = seg.ReadDataBuffer(0,
		// 0x3e8).TrimEnd(" ".ToCharArray());
		// node.AppendChild(node3);
		// }
		// foreach (IdocSegment segment in seg.ChildSegments)
		// {
		// node.AppendChild(this.GetXMLNodeFromSegment(segment, doc));
		// }
		// return node;
	}

	private boolean IsSegmentValid(String SegmentName) {
		SegmentName = SegmentName.trim();
		if (SegmentName.length() < 3) {
			return false;
		}
		if (((IdocSegment) this.getStoredSegments().get(SegmentName) == null)) {
			String str = SegmentName.substring(0, SegmentName.length() - 3);
			if ((IdocSegment) this.getStoredSegments().get(str) == null) {
				if (SegmentName.substring(1, 1) == "2") {
					str = SegmentName.substring(0, 1) + "1" + SegmentName.substring(2);
				} else {
					str = SegmentName.substring(0, 1) + "2" + SegmentName.substring(2);
				}
				if ((IdocSegment) this.getStoredSegments().get(str) == null) {
					str = str.substring(0, str.length() - 3);
					if ((IdocSegment) this.getStoredSegments().get(str) == null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void WriteSegment(String ret, IdocSegment seg) {
		String str = ret;
		ret = str + seg.getSegmentName() + " " + seg.getDescription() + "\r\n";
		ret = ret + seg.ReadDataBuffer(0, 0x3e8) + "\r\n";
		for (int i = 0; i < seg.getChildSegments().size(); i++) {
			this.WriteSegment(ret, seg.getChildSegments().get(i));
		}
	}
    private void WriteSegment( StringBuilder st, IdocSegment seg)
    {
        int maxOccur = seg.getMaxOccur();
        if (maxOccur <= 0)
        {
            maxOccur = 1;
        }
        String a = "<xsd:element name=\"";
        a.concat(Converts.OriginalNameToXmlName(seg.getSegmentName()));
        a.concat("\" msprop:SEGMENTMAXOCCUR=\"");
        a.concat(String.valueOf(maxOccur));
        a.concat("\" maxOccurs=\"");
        a.concat(String.valueOf(maxOccur));
        a.concat("\">");
        st.append(a);
//        st.append(String.concat(new Object[] { "<xsd:element name=\"", Converts.OriginalNameToXmlName(seg.getSegmentName()), "\" msprop:SEGMENTMAXOCCUR=\"", maxOccur, "\" maxOccurs=\"", maxOccur, "\">" }));
        st.append("<xsd:annotation>\n");
        st.append("<xsd:documentation>" + Converts.OriginalNameToXmlName(seg.getDescription().replace("&", "")) + "</xsd:documentation>\n ");
        st.append("</xsd:annotation>\n");
        st.append("<xsd:complexType>\n");
        st.append("<xsd:sequence>\n");
        for (IdocSegmentField field : seg.getFields())
        {
            st.append("<xsd:element name=\"" + Converts.OriginalNameToXmlName(field.getFieldName()) + "\" minOccurs=\"0\">\n");
            st.append("<xsd:annotation>\n");
            st.append("<xsd:documentation>" + Converts.OriginalNameToXmlName(field.getDescription().replace("&", "")) + "</xsd:documentation> \n");
            st.append("</xsd:annotation>\n");
            st.append("<xsd:simpleType>\n");
            st.append("<xsd:restriction base=\"xsd:string\">\n");
            st.append("<xsd:maxLength value=\"" + field.getExternalLength() + "\" /> \n");
            st.append("</xsd:restriction>\n");
            st.append("</xsd:simpleType>\n");
            st.append("</xsd:element>\n");
        }
        for (IdocSegment segment : seg.getChildSegments())
        {
            this.WriteSegment( st, segment);
        }
        st.append("</xsd:sequence>\n");
        st.append("<xsd:attribute name=\"SEGMENT\" use=\"required\">\n");
        st.append("<xsd:simpleType>\n");
        st.append("<xsd:restriction base=\"xsd:string\">\n");
        st.append("<xsd:enumeration value=\"1\"/>\n");
        st.append("</xsd:restriction>\n");
        st.append("</xsd:simpleType>\n");
        st.append("</xsd:attribute>\n");
        st.append("</xsd:complexType>\n");
        st.append("</xsd:element>\n");
    }
	private String CreateTid()
    {
        JCoFunction function = null;
		try {
			function = des.getRepository().getFunction("API_CREATE_TID");
			function.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String tid = function.getExportParameterList().getValue("TID").toString();
        return tid;
    }
	
	public  void Send()
    {
		this.Send(CreateTid());
    }
    public void Send(String tId)
    {
        //if (this.con == null)
        //{
        //    throw new ERPException(Messages.NoConnectionAssigned);
        //}
        //if ((this.con.RFCHandle <= 0) && (this.con.Protocol != ClientProtocol.HttpSoap))
        //{
        //    throw new ERPException(Messages.NoConnectionAssigned);
        //}
        //if (this._ChildSegments.Count == 0)
        //{
        //    throw new ERPException(Messages.Nosegmentsfound);
        //}
       // RFCFunction function = RFCFunctionFactory.GenerateFunctionObjectForIDOC_INBOUND_ASYNCHRONOUS(this.con.IsUnicode);
        JCoFunction function = null;
		try {
			function = des.getRepository().getFunction("IDOC_INBOUND_ASYNCHRONOUS");
		} catch (JCoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //function.Connection = this.con;
        JCoTable table = function.getTableParameterList().getTable("IDOC_CONTROL_REC_40");
        table.appendRow();
        
//        IRfcStructure structure = table.CurrentRow;
        table.setValue("TABNAM", this.TABNAM);
        table.setValue("TABNAM",this.TABNAM);
        table.setValue("MANDT",this.MANDT);
        table.setValue("DOCNUM",this.DOCNUM);
        table.setValue("DOCREL",this.DOCREL);
        table.setValue("STATUS",this.STATUS);
        table.setValue("DIRECT",this.DIRECT);
        table.setValue("OUTMOD",this.OUTMOD);
        table.setValue("EXPRSS",this.EXPRSS);
        table.setValue("IDOCTYP",this.IDOCTYP);
        table.setValue("CIMTYP",this.CIMTYP);
        table.setValue("MESTYP",this.MESTYP);
        table.setValue("MESCOD",this.MESCOD);
        table.setValue("MESFCT",this.MESFCT);
        table.setValue("STD",this.STD);
        table.setValue("STDVRS",this.STDVRS);
        table.setValue("STDMES",this.STDMES);
        table.setValue("SNDPOR",this.SNDPOR);
        table.setValue("SNDPRT",this.SNDPRT);
        table.setValue("SNDPFC",this.SNDPFC);
        table.setValue("SNDPRN",this.SNDPRN);
        table.setValue("SNDSAD",this.SNDSAD);
        table.setValue("SNDLAD",this.SNDLAD);
        table.setValue("RCVPOR",this.RCVPOR);
        table.setValue("RCVPRT",this.RCVPRT);
        table.setValue("RCVPFC",this.RCVPFC);
        table.setValue("RCVPRN",this.RCVPRN);
        table.setValue("RCVSAD",this.RCVSAD);
        table.setValue("RCVLAD",this.RCVLAD);
        table.setValue("CREDAT",this.CREDAT);
        table.setValue("CRETIM",this.CRETIM);
        table.setValue("REFINT",this.REFINT);
        table.setValue("REFGRP",this.REFGRP);
        table.setValue("REFMES",this.REFMES);
        table.setValue("ARCKEY",this.ARCKEY);
        table.setValue("SERIAL",this.SERIAL);
        JCoTable tData = function.getTableParameterList().getTable("IDOC_DATA_REC_40");
        for (int i = 0; i < this.getSegments().size(); i++)
        {
            this.WriteSegmentToTable("0000", tData, this.getSegments().get(i), 1);
        }

        try {
        	des.confirmTID(tId);
			function.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // function.Execute(TID);
       

      //  this._LastTID = Encoding.Default.GetString(TID);
        this.LastTID = tId.toString();
    }
    
    public  boolean SendAndWait()
    {
        //if (this.con == null)
        //{
        //    throw new ERPException(Messages.NoConnectionAssigned);
        //}
        //if ((this.con.RFCHandle <= 0) && (this.con.Protocol != ClientProtocol.HttpSoap))
        //{
        //    throw new ERPException(Messages.NoConnectionAssigned);
        //}
        //if (this._ChildSegments.Count == 0)
        //{
        //    throw new ERPException(Messages.Nosegmentsfound);
        //}
        JCoFunction function = null;
		try {
			function = this.des.getRepository().getFunction("IDOC_INBOUND_SINGLE");
		} catch (JCoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        function.getImportParameterList().setValue("PI_DO_COMMIT", "X");
//        function["PI_DO_COMMIT"].SetValue("X");
       // function.Connection = this.con;
        JCoRecord structure = function.getImportParameterList().getStructure("PI_IDOC_CONTROL_REC_40");
        structure.setValue("TABNAM",this.TABNAM);
        structure.setValue("MANDT",this.MANDT);
        structure.setValue("DOCNUM",this.DOCNUM);
        structure.setValue("DOCREL",this.DOCREL);
        structure.setValue("STATUS",this.STATUS);
        structure.setValue("DIRECT",this.DIRECT.trim().equals("") ? "2" : this.DIRECT);
        structure.setValue("OUTMOD",this.OUTMOD);
        structure.setValue("EXPRSS",this.EXPRSS);
        structure.setValue("IDOCTYP",this.IDOCTYP);
        structure.setValue("CIMTYP",this.CIMTYP);
        structure.setValue("MESTYP",this.MESTYP);
        structure.setValue("MESCOD",this.MESCOD);
        structure.setValue("MESFCT",this.MESFCT);
        structure.setValue("STD"   ,this.STD);
        structure.setValue("STDVRS",this.STDVRS);
        structure.setValue("STDMES",this.STDMES);
        structure.setValue("SNDPOR",this.SNDPOR);
        structure.setValue("SNDPRT",this.SNDPRT);
        structure.setValue("SNDPFC",this.SNDPFC);
        structure.setValue("SNDPRN",this.SNDPRN);
        structure.setValue("SNDSAD",this.SNDSAD);
        structure.setValue("SNDLAD",this.SNDLAD);
        structure.setValue("RCVPOR",this.RCVPOR);
        structure.setValue("RCVPRT",this.RCVPRT);
        structure.setValue("RCVPFC",this.RCVPFC);
        structure.setValue("RCVPRN",this.RCVPRN);
        structure.setValue("RCVSAD",this.RCVSAD);
        structure.setValue("RCVLAD",this.RCVLAD);
        structure.setValue("CREDAT",this.CREDAT);
        structure.setValue("CRETIM",this.CRETIM);
        structure.setValue("REFINT",this.REFINT);
        structure.setValue("REFGRP",this.REFGRP);
        structure.setValue("REFMES",this.REFMES);
        structure.setValue("ARCKEY",this.ARCKEY);
        structure.setValue("SERIAL",this.SERIAL);
        JCoTable tData = function.getImportParameterList().getTable("PT_IDOC_DATA_RECORDS_40");
        for (int i = 0; i < this.getSegments().size(); i++)
        {
            this.WriteSegmentToTable("0000", tData, this.getSegments().get(i), 1);
        }
        try {
			function.execute(des);
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.DOCNUM = function.getExportParameterList().getValue("PE_IDOC_NUMBER").toString();
        return function.getExportParameterList().getValue("PE_ERROR_PRIOR_TO_APPLICATION").toString().equals("X");
    }
    
    private void WriteSegmentToTable(String ParentNr, JCoTable tData, IdocSegment SegToWrite, int HLevel)
    {
        tData.appendRow();
//        IRfcStructure structure = tData.CurrentRow;
        //RFCStructure structure = tData.AddRow();
        String parentNr = String.valueOf(tData.getNumRows());
        String str2 = SegToWrite.ReadDataBuffer(0, 0x3e8);
        tData.setValue("SEGNAM", SegToWrite.getSegmentName());
        tData.setValue("MANDT", this.MANDT);
        tData.setValue("DOCNUM", this.DOCNUM);
        tData.setValue("SEGNUM", StringUtils.rightPad(parentNr, 6,"0"));
        tData.setValue("PSGNUM", StringUtils.rightPad(ParentNr, 6,"0"));
        tData.setValue("DOCNUM", this.DOCNUM);
        
        if ((HLevel == 1) && (SegToWrite.getMaxOccur() > 1))
        {
            HLevel++;
        }
        if (this.SkipHLevel)
        {
            //structure["HLEVEL"].SetValue("  ");
            tData.setValue("HLEVEL", "  ");
        }
        else
        {
        	tData.setValue("HLEVEL", StringUtils.rightPad(String.valueOf(HLevel), 2,"0"));
//            structure["HLEVEL"].SetValue(Convert.ToString(HLevel).PadLeft(2, "0".ToCharArray()[0]));
        }
        tData.setValue("SDATA", str2);
//        structure["SDATA"].SetValue(str2);
        if (SegToWrite.HasChildren())
        {
            for (int i = 0; i < SegToWrite.getChildSegments().size(); i++)
            {
                this.WriteSegmentToTable(parentNr, tData, SegToWrite.getChildSegments().get(i), HLevel + 1);
            }
        }
    }

    
    private void RegenerateFields()
    {
        for (int i = 0; i < this.getSegments().size(); i++)
        {
            this.RegenerateSegment(this.getSegments().get(i));
        }
    }
    
	private void RegenerateSegment(IdocSegment seg) {

		if (seg.getFields().size() == 0) {

			IdocSegment segment = this.CreateSegment(seg.getSegmentName());
			String str = seg.ReadDataBuffer(0, 0x3e8);
			seg.setFields(segment.getFields());
			for (IdocSegmentField field : seg.getFields()) {
				field.setFieldValue(str.substring(field.getOffsetInBuffer(), field.getExternalLength()));
			}
		}

		for (int i = 0; i < seg.getChildSegments().size(); i++) {
			this.RegenerateSegment(seg.getChildSegments().get(i));
		}

	}

	public void StoreSegmentForFurtherUse(IdocSegment seg) {
		if (!this.StoredSegments.contains(seg.getSegmentName())) {
			this.StoredSegments.put(seg.getSegmentName(), seg);
		}
	}
	@Override
    public String toString()
    {
        String ret = this.IDOCTYP + "\r\n";
        for (int i = 0; i < this.getSegments().size(); i++)
        {
            this.WriteSegment( ret, this.getSegments().get(i));
        }
        return ret;
    }
    

	/**
	 * @return the aRCKEY
	 */
	public String getARCKEY() {
		return ARCKEY;
	}

	/**
	 * @param aRCKEY
	 *            the aRCKEY to set
	 */
	public void setARCKEY(String aRCKEY) {
		ARCKEY = aRCKEY;
	}

	/**
	 * @return the childSegments
	 */
	public IdocSegmentCollection getSegments() {
		return ChildSegments;
	}

	/**
	 * @param childSegments
	 *            the childSegments to set
	 */
	public void setSegments(IdocSegmentCollection childSegments) {
		ChildSegments = childSegments;
	}

	/**
	 * @return the cIMTYP
	 */
	public String getCIMTYP() {
		return CIMTYP;
	}

	/**
	 * @param cIMTYP
	 *            the cIMTYP to set
	 */
	public void setCIMTYP(String cIMTYP) {
		CIMTYP = cIMTYP;
	}

	/**
	 * @return the cREDAT
	 */
	public String getCREDAT() {
		return CREDAT;
	}

	/**
	 * @param cREDAT
	 *            the cREDAT to set
	 */
	public void setCREDAT(String cREDAT) {
		CREDAT = cREDAT;
	}

	/**
	 * @return the cRETIM
	 */
	public String getCRETIM() {
		return CRETIM;
	}

	/**
	 * @param cRETIM
	 *            the cRETIM to set
	 */
	public void setCRETIM(String cRETIM) {
		CRETIM = cRETIM;
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
	 * @return the dIRECT
	 */
	public String getDIRECT() {
		return DIRECT;
	}

	/**
	 * @param dIRECT
	 *            the dIRECT to set
	 */
	public void setDIRECT(String dIRECT) {
		DIRECT = dIRECT;
	}

	/**
	 * @return the dOCNUM
	 */
	public String getDOCNUM() {
		return DOCNUM;
	}

	/**
	 * @param dOCNUM
	 *            the dOCNUM to set
	 */
	public void setDOCNUM(String dOCNUM) {
		DOCNUM = dOCNUM;
	}

	/**
	 * @return the dOCREL
	 */
	public String getDOCREL() {
		return DOCREL;
	}

	/**
	 * @param dOCREL
	 *            the dOCREL to set
	 */
	public void setDOCREL(String dOCREL) {
		DOCREL = dOCREL;
	}

	/**
	 * @return the eXPRSS
	 */
	public String getEXPRSS() {
		return EXPRSS;
	}

	/**
	 * @param eXPRSS
	 *            the eXPRSS to set
	 */
	public void setEXPRSS(String eXPRSS) {
		EXPRSS = eXPRSS;
	}

	/**
	 * @return the iDOCTYP
	 */
	public String getIDOCTYP() {
		return IDOCTYP;
	}

	/**
	 * @param iDOCTYP
	 *            the iDOCTYP to set
	 */
	public void setIDOCTYP(String iDOCTYP) {
		IDOCTYP = iDOCTYP;
	}

	/**
	 * @return the lastTID
	 */
	public String getLastTID() {
		return LastTID;
	}

	/**
	 * @param lastTID
	 *            the lastTID to set
	 */
	public void setLastTID(String lastTID) {
		LastTID = lastTID;
	}

	/**
	 * @return the mANDT
	 */
	public String getMANDT() {
		return MANDT;
	}

	/**
	 * @param mANDT
	 *            the mANDT to set
	 */
	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	/**
	 * @return the mESCOD
	 */
	public String getMESCOD() {
		return MESCOD;
	}

	/**
	 * @param mESCOD
	 *            the mESCOD to set
	 */
	public void setMESCOD(String mESCOD) {
		MESCOD = mESCOD;
	}

	/**
	 * @return the mESFCT
	 */
	public String getMESFCT() {
		return MESFCT;
	}

	/**
	 * @param mESFCT
	 *            the mESFCT to set
	 */
	public void setMESFCT(String mESFCT) {
		MESFCT = mESFCT;
	}

	/**
	 * @return the mESTYP
	 */
	public String getMESTYP() {
		return MESTYP;
	}

	/**
	 * @param mESTYP
	 *            the mESTYP to set
	 */
	public void setMESTYP(String mESTYP) {
		MESTYP = mESTYP;
	}

	/**
	 * @return the oUTMOD
	 */
	public String getOUTMOD() {
		return OUTMOD;
	}

	/**
	 * @param oUTMOD
	 *            the oUTMOD to set
	 */
	public void setOUTMOD(String oUTMOD) {
		OUTMOD = oUTMOD;
	}

	/**
	 * @return the rCVLAD
	 */
	public String getRCVLAD() {
		return RCVLAD;
	}

	/**
	 * @param rCVLAD
	 *            the rCVLAD to set
	 */
	public void setRCVLAD(String rCVLAD) {
		RCVLAD = rCVLAD;
	}

	/**
	 * @return the rCVPFC
	 */
	public String getRCVPFC() {
		return RCVPFC;
	}

	/**
	 * @param rCVPFC
	 *            the rCVPFC to set
	 */
	public void setRCVPFC(String rCVPFC) {
		RCVPFC = rCVPFC;
	}

	/**
	 * @return the rCVPOR
	 */
	public String getRCVPOR() {
		return RCVPOR;
	}

	/**
	 * @param rCVPOR
	 *            the rCVPOR to set
	 */
	public void setRCVPOR(String rCVPOR) {
		RCVPOR = rCVPOR;
	}

	/**
	 * @return the rCVPRN
	 */
	public String getRCVPRN() {
		return RCVPRN;
	}

	/**
	 * @param rCVPRN
	 *            the rCVPRN to set
	 */
	public void setRCVPRN(String rCVPRN) {
		RCVPRN = rCVPRN;
	}

	/**
	 * @return the rCVPRT
	 */
	public String getRCVPRT() {
		return RCVPRT;
	}

	/**
	 * @param rCVPRT
	 *            the rCVPRT to set
	 */
	public void setRCVPRT(String rCVPRT) {
		RCVPRT = rCVPRT;
	}

	/**
	 * @return the rCVSAD
	 */
	public String getRCVSAD() {
		return RCVSAD;
	}

	/**
	 * @param rCVSAD
	 *            the rCVSAD to set
	 */
	public void setRCVSAD(String rCVSAD) {
		RCVSAD = rCVSAD;
	}

	/**
	 * @return the rEFGRP
	 */
	public String getREFGRP() {
		return REFGRP;
	}

	/**
	 * @param rEFGRP
	 *            the rEFGRP to set
	 */
	public void setREFGRP(String rEFGRP) {
		REFGRP = rEFGRP;
	}

	/**
	 * @return the rEFINT
	 */
	public String getREFINT() {
		return REFINT;
	}

	/**
	 * @param rEFINT
	 *            the rEFINT to set
	 */
	public void setREFINT(String rEFINT) {
		REFINT = rEFINT;
	}

	/**
	 * @return the rEFMES
	 */
	public String getREFMES() {
		return REFMES;
	}

	/**
	 * @param rEFMES
	 *            the rEFMES to set
	 */
	public void setREFMES(String rEFMES) {
		REFMES = rEFMES;
	}

	/**
	 * @return the sERIAL
	 */
	public String getSERIAL() {
		return SERIAL;
	}

	/**
	 * @param sERIAL
	 *            the sERIAL to set
	 */
	public void setSERIAL(String sERIAL) {
		SERIAL = sERIAL;
	}

	/**
	 * @return the skipHLevel
	 */
	public boolean isSkipHLevel() {
		return SkipHLevel;
	}

	/**
	 * @param skipHLevel
	 *            the skipHLevel to set
	 */
	public void setSkipHLevel(boolean skipHLevel) {
		SkipHLevel = skipHLevel;
	}

	/**
	 * @return the sNDLAD
	 */
	public String getSNDLAD() {
		return SNDLAD;
	}

	/**
	 * @param sNDLAD
	 *            the sNDLAD to set
	 */
	public void setSNDLAD(String sNDLAD) {
		SNDLAD = sNDLAD;
	}

	/**
	 * @return the sNDPFC
	 */
	public String getSNDPFC() {
		return SNDPFC;
	}

	/**
	 * @param sNDPFC
	 *            the sNDPFC to set
	 */
	public void setSNDPFC(String sNDPFC) {
		SNDPFC = sNDPFC;
	}

	/**
	 * @return the sNDPOR
	 */
	public String getSNDPOR() {
		return SNDPOR;
	}

	/**
	 * @param sNDPOR
	 *            the sNDPOR to set
	 */
	public void setSNDPOR(String sNDPOR) {
		SNDPOR = sNDPOR;
	}

	/**
	 * @return the sNDPRN
	 */
	public String getSNDPRN() {
		return SNDPRN;
	}

	/**
	 * @param sNDPRN
	 *            the sNDPRN to set
	 */
	public void setSNDPRN(String sNDPRN) {
		SNDPRN = sNDPRN;
	}

	/**
	 * @return the sNDPRT
	 */
	public String getSNDPRT() {
		return SNDPRT;
	}

	/**
	 * @param sNDPRT
	 *            the sNDPRT to set
	 */
	public void setSNDPRT(String sNDPRT) {
		SNDPRT = sNDPRT;
	}

	/**
	 * @return the sNDSAD
	 */
	public String getSNDSAD() {
		return SNDSAD;
	}

	/**
	 * @param sNDSAD
	 *            the sNDSAD to set
	 */
	public void setSNDSAD(String sNDSAD) {
		SNDSAD = sNDSAD;
	}

	/**
	 * @return the sTATUS
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	/**
	 * @return the sTD
	 */
	public String getSTD() {
		return STD;
	}

	/**
	 * @param sTD
	 *            the sTD to set
	 */
	public void setSTD(String sTD) {
		STD = sTD;
	}

	/**
	 * @return the sTDMES
	 */
	public String getSTDMES() {
		return STDMES;
	}

	/**
	 * @param sTDMES
	 *            the sTDMES to set
	 */
	public void setSTDMES(String sTDMES) {
		STDMES = sTDMES;
	}

	/**
	 * @return the sTDVRS
	 */
	public String getSTDVRS() {
		return STDVRS;
	}

	/**
	 * @param sTDVRS
	 *            the sTDVRS to set
	 */
	public void setSTDVRS(String sTDVRS) {
		STDVRS = sTDVRS;
	}

	/**
	 * @return the tABNAM
	 */
	public String getTABNAM() {
		return TABNAM;
	}

	/**
	 * @param tABNAM
	 *            the tABNAM to set
	 */
	public void setTABNAM(String tABNAM) {
		TABNAM = tABNAM;
	}

	/**
	 * @return the tEST
	 */
	public String getTEST() {
		return TEST;
	}

	/**
	 * @param tEST
	 *            the tEST to set
	 */
	public void setTEST(String tEST) {
		TEST = tEST;
	}

	/**
	 * @return the des
	 */
	public JCoDestination getDes() {
		return des;
	}

	/**
	 * @param des
	 *            the des to set
	 */
	public void setDes(JCoDestination des) {
		this.des = des;
	}

	/**
	 * @return the con
	 */
	public SAPConnection getCon() {
		return con;
	}

	/**
	 * @param con
	 *            the con to set
	 */
	public void setCon(SAPConnection con) {
		this.con = con;
	}

	/**
	 * @return the plainLoadSegmentHash
	 */
	public Hashtable getPlainLoadSegmentHash() {
		return PlainLoadSegmentHash;
	}

	/**
	 * @param plainLoadSegmentHash
	 *            the plainLoadSegmentHash to set
	 */
	public void setPlainLoadSegmentHash(Hashtable plainLoadSegmentHash) {
		PlainLoadSegmentHash = plainLoadSegmentHash;
	}

	/**
	 * @return the plainTextEscapes
	 */
	public static Dictionary<String, String> getPlainTextEscapes() {
		return plainTextEscapes;
	}

	/**
	 * @param plainTextEscapes
	 *            the plainTextEscapes to set
	 */
	public static void setPlainTextEscapes(Dictionary<String, String> plainTextEscapes) {
		Idoc.plainTextEscapes = plainTextEscapes;
	}

	/**
	 * @return the plainTextUnescapes
	 */
	public static Dictionary<String, String> getPlainTextUnescapes() {
		return plainTextUnescapes;
	}

	/**
	 * @param plainTextUnescapes
	 *            the plainTextUnescapes to set
	 */
	public static void setPlainTextUnescapes(Dictionary<String, String> plainTextUnescapes) {
		Idoc.plainTextUnescapes = plainTextUnescapes;
	}

	/**
	 * @return the segNumForPlainFile
	 */
	public int getSegNumForPlainFile() {
		return SegNumForPlainFile;
	}

	/**
	 * @param segNumForPlainFile
	 *            the segNumForPlainFile to set
	 */
	public void setSegNumForPlainFile(int segNumForPlainFile) {
		SegNumForPlainFile = segNumForPlainFile;
	}

	/**
	 * @return the storedSegments
	 */
	public Hashtable getStoredSegments() {
		return StoredSegments;
	}

	/**
	 * @param storedSegments
	 *            the storedSegments to set
	 */
	public void setStoredSegments(Hashtable storedSegments) {
		StoredSegments = storedSegments;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
