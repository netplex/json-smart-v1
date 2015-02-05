package net.minidev.json.test;

import java.math.BigDecimal;

import junit.framework.TestCase;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

public class TestBigDecimal extends TestCase {
	public void testBigDecimal() {
		BigDecimal test = new BigDecimal(1);
		JSONObject o = new JSONObject();
		o.put("a", test);
		String comp = JSONValue.toJSONString(o, JSONStyle.MAX_COMPRESS);
		assertEquals("{a:1}", comp);
	}
	
	public void testSupportsOwnOutput() {
		String bad = "{\"a\":0.050000000000000003}";
	    assertEquals(bad, JSONValue.toJSONString(JSONValue.parse(bad)));
	}
}
