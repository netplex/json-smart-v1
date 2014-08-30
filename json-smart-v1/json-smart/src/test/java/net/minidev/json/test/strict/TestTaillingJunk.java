package net.minidev.json.test.strict;

import junit.framework.TestCase;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.minidev.json.test.MustThrows;

/**
 * @since 1.0.7
 */
public class TestTaillingJunk extends TestCase {

	public void testTaillingSpace() throws Exception {
		String s = "{\"t\":0}   ";
		JSONObject o = (JSONObject) new JSONParser(JSONParser.MODE_STRICTEST).parse(s);
		assertEquals(o.get("t"), 0);
	}

	public void testTaillingSpace2() throws Exception {
		String s = "{\"t\":0}   \r\n ";
		JSONObject o = (JSONObject) new JSONParser(JSONParser.MODE_STRICTEST).parse(s);
		assertEquals(o.get("t"), 0);
	}

	public void testTaillingData() throws Exception {
		String s = "{\"t\":0}  0";
		MustThrows.testStrictestInvalidJson(s, ParseException.ERROR_UNEXPECTED_TOKEN);
	}

	public void testTaillingDataPermisive() throws Exception {
		String s = "{\"t\":0}  0";
		JSONObject o = (JSONObject) new JSONParser(JSONParser.MODE_PERMISSIVE).parse(s);
		assertEquals(o.get("t"), 0);
	}
}
