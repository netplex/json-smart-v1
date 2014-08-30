package net.minidev.json.test;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import junit.framework.TestCase;

public class TestKeyword extends TestCase {
	
	public void testBool() throws Exception {
		String s = "{t:true}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), true);
		
		s = "{t:false}";
		o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), false);
	}
	
	public void testNull() throws Exception {
		String s = "{t:null}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertNull(o.get("t"));
	}
	
	public void testNaN() throws Exception {
		String s = "{t:NaN}";
		JSONObject o = (JSONObject) new JSONParser().parse(s);
		assertEquals(o.get("t"), Float.NaN);
	}

}
