package net.minidev.json.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import org.junit.Test;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import junit.framework.TestCase;

public class TestBigValue extends TestCase {
	String bigStr = "12345678901234567890123456789";

	/**
	 * test BigDecimal serialization
	 */
	@Test
	public void testBigDecimal() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		BigDecimal bigDec = new BigDecimal(bigStr + "." + bigStr);
		map.put("big", bigDec);
		String test = JSONValue.toJSONString(map);
		String result = "{\"big\":" + bigStr + "." +bigStr + "}";
		assertEquals(result, test);
		JSONObject obj =  (JSONObject)JSONValue.parse(test);
		assertEquals(bigDec, obj.get("big"));
		assertEquals(bigDec.getClass(), obj.get("big").getClass());
	}

	/**
	 * test BigInteger serialization
	 */
	@Test
	public void testBigInteger() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		BigInteger bigInt = new BigInteger(bigStr);
		map.put("big", bigInt);
		String test = JSONValue.toJSONString(map);
		String result = "{\"big\":" + bigStr + "}";
		assertEquals(result, test);
		JSONObject obj =  (JSONObject)JSONValue.parse(test);
		assertEquals(bigInt, obj.get("big"));
		assertEquals(bigInt.getClass(), obj.get("big").getClass());
	}
	/**
	 * https://github.com/netplex/json-smart-v1/issues/6
	 */
	@Test
    public void testBigDouble() throws Exception {
        String content = "{\"customDouble\":3.14159265358979323846}";
        // System.out.printf("Input:  %s\n", content);
        JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jwtContent = (JSONObject) parser.parse(content);
        String serialized = jwtContent.toJSONString(); 
        // System.out.printf("Output: %s\n", serialized);
        assertEquals("should not loose precision", serialized, content);
    }
}
