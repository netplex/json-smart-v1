package net.minidev.json.test;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import junit.framework.TestCase;

public class TestString extends TestCase {

	public void testS1() throws Exception {
		String text = "My Test";
		String s = "{t:\"" + text + "\"}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), text);
	}

	public void testS2() throws Exception {
		String text = "My Test";
		String s = "{t:'" + text + "'}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), text);
	}

	public void testSEscape() throws Exception {
		String text = "My\r\nTest";
		String text2 = "My\\r\\nTest";
		String s = "{t:'" + text2 + "'}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), text);
	}
}
