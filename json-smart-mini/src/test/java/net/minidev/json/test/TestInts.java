package net.minidev.json.test;

import java.math.BigInteger;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import junit.framework.TestCase;

public class TestInts extends TestCase {

	public void testIntMax() throws Exception {
		String s = "{t:" + Integer.MAX_VALUE + "}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), Integer.MAX_VALUE);
	}

	public void testIntMin() throws Exception {
		String s = "{t:" + Integer.MIN_VALUE + "}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), Integer.MIN_VALUE);
	}

	public void testInt() throws Exception {
		String s = "{t:90}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), 90);
	}

	public void testIntNeg() throws Exception {
		String s = "{t:-90}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), -90);
	}

	public void testBigInt() throws Exception {
		String bigText = Integer.MAX_VALUE + "" + Integer.MAX_VALUE + "" + Integer.MAX_VALUE + "" + Integer.MAX_VALUE;
		BigInteger big = new BigInteger(bigText, 10);
		String s = "{t:" + bigText + "}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), big);
	}
}
