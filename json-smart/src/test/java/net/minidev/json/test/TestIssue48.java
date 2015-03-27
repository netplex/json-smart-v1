package net.minidev.json.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ContentHandler;
import net.minidev.json.parser.ParseException;

/**
 * Catches issue 48: "JSONValue.parse does not correctly decode UTF-8 bytes from an inputstream"
 * mentioned in https://code.google.com/p/json-smart/issues/detail?id=48
 */
public class TestIssue48 extends TestCase {
	/**
	 * Covers the case described in https://code.google.com/p/json-smart/issues/detail?id=48
	 */
	public void testIssue48() throws UnsupportedEncodingException { 
		final String testJsonString = "{\"balance\":1000.21,\"num\":100,\"nickname\":null,\"is_vip\":true,\"Sinhalese\":\"සිංහල ජාතිය\",\"name\":\"foo\"}";
      
		final ByteArrayInputStream bis = new ByteArrayInputStream(testJsonString.getBytes("UTF-8"));
      
		final JSONObject obj = (JSONObject) JSONValue.parse(bis);

		final String actual = (String) obj.get("Sinhalese");
      
		assertEquals("සිංහල ජාතිය",  actual);
	}
   
	/**
	 * Covers the same issue seen when using SAXParse with an InputStream source drawing on bytes of UTF-8
	 */
	public void testIssue48_SAXParse_InputStream_UTF8() throws Exception {
		final String originalText = "僧伽罗人";
		final String testJsonString = "[ \"" + originalText + "\" ]";
      
		final ByteArrayInputStream bis = new ByteArrayInputStream(testJsonString.getBytes("UTF-8"));

		JSONValue.SAXParse(bis, new AssertingHandler(originalText));
	}
   
	/**
	 * Covers the same issue seen when using SAXParse with an InputStream source drawing on bytes of UTF-16
	 */
	public void testIssue48_SAXParse_InputStream_UTF16() throws Exception {
		final String originalText = "僧伽罗人";
		final String testJsonString = "[ \"" + originalText + "\" ]";

		final ByteArrayInputStream bis = new ByteArrayInputStream(testJsonString.getBytes("UTF-16"));

		JSONValue.SAXParse(bis, new AssertingHandler(originalText));
	}
   
	/**
	 * Covers the same issue seen when using SAXParse with a Reader source.
	 * 
	 * A Reader doesn't seem to be affected by the issue, probably because it reads 
	 * a character at a time, rather than a byte at a time. 
	 */
	public void testIssue48_SAXParse_Reader() throws Exception {
		final String originalText = "僧伽罗人";
		final String testJsonString = "[ \"" + originalText + "\" ]";
      
		final Reader source = new StringReader(testJsonString);
      
		JSONValue.SAXParse(source, new AssertingHandler(originalText));
	}
   
	/**
	 * A ContentHandler that checks any parsed primitive against a single expected 
	 * value and fails the test if it's not the same
	 */
	private static class AssertingHandler implements ContentHandler {
		final String expectedPrimitive;
      
		public AssertingHandler(final String expectedPrimitive) {
			this.expectedPrimitive = expectedPrimitive;
		}
      
		public void startJSON() throws ParseException, IOException { }
		public void endJSON() throws ParseException, IOException { }
		public boolean startObject() throws ParseException, IOException { return true; }
		public boolean endObject() throws ParseException, IOException { return true; }
		public boolean startObjectEntry(String key) throws ParseException, IOException { return true; }
		public boolean endObjectEntry() throws ParseException, IOException { return true; }
		public boolean startArray() throws ParseException, IOException { return true; }
		public boolean endArray() throws ParseException, IOException { return true; }
      
		public boolean primitive(final Object value) throws ParseException, IOException {
			assertEquals(this.expectedPrimitive, value);
			return true;
		}
	}
}
