package net.minidev.json.test;

import junit.framework.TestCase;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

public class BugReport extends TestCase {

	public void testIssue41() throws Exception {
		String test;
		test = "[{ 'Key' : [{''K2':['tab1']}]}]";
		test = test.replace('\'', '"');
		assertEquals(JSONValue.isValidJson(test), false);
		assertEquals(JSONValue.isValidJsonStrict(test), false);
	}

	public static void main(String[] args) {
		String test = "{'a':'b', 'c':'d'}";
		JSONObject obj =  (JSONObject)JSONValue.parse(test);
		System.out.println(obj.toString(JSONStyle.NO_COMPRESS));
	}
}
