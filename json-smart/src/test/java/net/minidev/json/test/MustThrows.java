package net.minidev.json.test;

import junit.framework.TestCase;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class MustThrows {

	public static void testStrictInvalidJson(String json, int execptionType) throws Exception {
		testInvalidJson(json, JSONParser.MODE_RFC4627, execptionType);
	}
	
	public static void testInvalidJson(String json, int permissifMode, int execptionType) throws Exception {
		JSONParser p = new JSONParser(execptionType);
		try {
			p.parse(json);
			TestCase.assertFalse("Exception Should Occure parsing:" + json, true);
		} catch (ParseException e) {
			if (execptionType == -1)
				execptionType = e.getErrorType();
			TestCase.assertEquals(execptionType, e.getErrorType());
		}
	}
}
