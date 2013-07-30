package net.minidev.json.test;

import junit.framework.TestCase;
import net.minidev.json.JSONValue;

public class BugReport extends TestCase {

	public void testIssue41() throws Exception {
		String test;
		test = "[{ 'Key' : [{''K2':['tab1']}]}]";
		test = test.replace('\'', '"');
		assertEquals(JSONValue.isValidJson(test), false);
		assertEquals(JSONValue.isValidJsonStrict(test), false);
	}

}
