package net.minidev.json.test;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import junit.framework.TestCase;

public class TestFloat extends TestCase {

	public void testFloat() throws Exception {
		String[] asNumber = new String[] { "1.0", "123.456", "1.0E1", "123.456E12", "1.0E+1", "123.456E+12", "1.0E-1",
				"123.456E-12", "1.0e1", "123.456e12", "1.0e+1", "123.456e+12", "1.0e-1", "123.456e-12" };
		JSONParser p = new JSONParser();
		for (String s : asNumber) {
			String json = "{v:" + s + "}";
			Double val = Double.valueOf(s.trim());
			JSONObject obj = (JSONObject) p.parse(json);
			Object value = obj.get("v");
			assertEquals("Should be parse as double", val, value);
		}
	}

	public void testNonFloat() throws Exception {
		String[] asStr = new String[] { "1.0%", "123.45.6", "1.0E", "++123.456E12", "+-01", "1.0E+1.2" };
		JSONParser p = new JSONParser();
		for (String s : asStr) {
			String json = "{v:" + s + "}";
			JSONObject obj = (JSONObject) p.parse(json);
			assertEquals("Should be parse as string", s, obj.get("v"));

			String correct = "{\"v\":\"" + s + "\"}";
			assertEquals("Should be re serialized as", correct, obj.toJSONString());
		}
	}
}
