package com.test;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "{data:[\"first\",\"second\"]}";
		ArrayList<String> list = (ArrayList<String>)JSONArray.toCollection(JSONObject.fromObject(s).getJSONArray("data"));
		System.out.println(list);
	}

}
