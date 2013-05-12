package com.sapint.datatable;

import java.util.ArrayList;
import java.util.HashMap;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import data.DataTable;

public class DataTableUtil {

	/**
	 * 王卫生，2013/2/6
	 * 由于DataTable中的asXmlText函数生成的xml格式并能满足要求，它使用属性的方式存储内容。
	 * 现在产生的方式为用子节点的方式存储。
	 * 还有，原DataColumn中getLabel()是有Bug,当没有label时，出抛出null的错误。
	 * 生成XML文档时特别要注意排除非法的元素名字，比如这里就遇到一个正斜杠的问题，在SAP中的字段是可以包含正斜杠的。
	 * 在这里把正斜杠全部转换成下划线_.
	 * @param args
	 */
	public static String dataTableToXml(DataTable d) {
		try {
			Document xmlTableDoc = DocumentHelper.createDocument();
			xmlTableDoc.setXMLEncoding("UTF-8");
			Element tableElement = xmlTableDoc.addElement("table");
			tableElement.addAttribute("name", d.getTableName());
			tableElement.addAttribute("readonly",
					String.valueOf(d.isReadOnly()));
			Element columensElement = tableElement.addElement("columns");
			for (int i = 0; i < d.getColumns().size(); i++) {
				String name = d.getColumns().get(i).getColumnName().toString();
				String caption = d.getColumns().get(i).getLabel();
				String type = String.valueOf(d.getColumns().get(i)
						.getDataType());
				String typename = d.getColumns().get(i).getDataTypeName();

				Element columenElement = columensElement.addElement("column");
				Element nextElement;
				nextElement = columenElement.addElement("name");
				name = name.replaceAll("/", "_");
				nextElement.setText(name);

				nextElement = columenElement.addElement("caption");
				if (caption != null) {
					nextElement.setText(caption);
				} else {
					nextElement.setText("");
				}

				nextElement = columenElement.addElement("type");
				nextElement.setText(String.valueOf(type));

				nextElement = columenElement.addElement("typename");
				nextElement.setText(typename);

			}
			Element rowsElement = tableElement.addElement("rows");
			for (int i = 0; i < d.getRows().size(); i++) {
				Element rowElement = rowsElement.addElement("row");
				for (int j = 0; j < d.getColumns().size(); j++) {
					String key = d.getColumns().get(j).getColumnName();
					String val = d.getRows().get(i).getString(j);
					Element nextElement;
					if (key.contains("/")) {
						System.out.println(key);
						key = key.replaceAll("/", "_");
						System.out.println(key);
					}
					key = key.replaceAll("/", "_");
					nextElement = rowElement.addElement(key);
					nextElement.setText(val);
				}

			}

			return xmlTableDoc.asXML();
		} catch (Exception e) {
			System.out.print("DataTable生成XML错误:" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把dataTable转换成Json格式
	 * @param d
	 * @return
	 */
	public static String dataTableToJSON(DataTable d) {
		try {
			ArrayList<HashMap<String, String>> columns = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> a = null;
			for (int i = 0; i < d.getColumns().size(); i++) {
				a = new HashMap<String, String>();

				String name = d.getColumns().get(i).getColumnName().toString();
				String caption = d.getColumns().get(i).getLabel();
				String type = String.valueOf(d.getColumns().get(i)
						.getDataType());
				String typename = d.getColumns().get(i).getDataTypeName();
				a.put("name", name);
				a.put("caption", caption);
				a.put("type", type);
				a.put("typename", typename);
				columns.add(a);
			}

			ArrayList<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < d.getRows().size(); i++) {
				HashMap<String, String> b = new HashMap<String, String>();
				for (int j = 0; j < d.getColumns().size(); j++) {
					String key = d.getColumns().get(j).getColumnName();
					String val = d.getRows().get(i).getString(j);
					b.put(key, val);
				}
				rows.add(b);
			}
			HashMap<String, ArrayList<HashMap<String, String>>> c = new HashMap<String, ArrayList<HashMap<String, String>>>();
			c.put("columns", columns);
			c.put("rows", rows);
			JSONObject jsonObject = JSONObject.fromObject(c);
			System.out.println(jsonObject);
			return jsonObject.toString();
		} catch (Exception e) {
			System.out.print("DataTable生成JSON错误:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}

}
