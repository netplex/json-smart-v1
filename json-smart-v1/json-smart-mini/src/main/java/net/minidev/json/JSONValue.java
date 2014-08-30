/*
 * $Id: JSONValue.java,v 1.1 2006/04/15 14:37:04 platform Exp $
 * Created on 2006-4-15
 */
package net.minidev.json;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import net.minidev.json.parser.ContainerFactory;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.JSONParserStream;
import net.minidev.json.parser.ParseException;

/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONValue {

	/**
	 * Parse JSON text into java object from the input source. Please use
	 * parseWithException() if you don't want to ignore the exception.
	 * 
	 * @see JSONParser#parse(Reader)
	 * @see #parseWithException(Reader)
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 * 
	 */
	public static Object parse(Reader in) {
		try {
			return new JSONParserStream().parse(in);
		} catch (Exception e) {
			return null;
		}
	}

	public static Object parse(String s) {
		try {
			return new JSONParser().parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Parse JSON text into java object from the input source.
	 * 
	 * @see parser.JSONParser
	 * 
	 * @return Instance of the following: JSONObject, JSONArray, String,
	 *         java.lang.Number, java.lang.Boolean, null
	 */
	public static Object parseWithException(Reader in) throws IOException, ParseException {
		return new JSONParserStream().parse(in, ContainerFactory.FACTORY);
	}

	public static Object parseWithException(String s) throws ParseException {
		return new JSONParser().parse(s, ContainerFactory.FACTORY);
	}

	/**
	 * Encode an object into JSON text and write it to out.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONStreamAware or a
	 * JSONAware, JSONStreamAware or JSONAware will be considered firstly.
	 * <p>
	 * 
	 * @see JSONObject#writeJSONString(Map, Appendable)
	 * @see JSONArray#writeJSONString(List, Appendable)
	 */
	@SuppressWarnings("unchecked")
	public static void writeJSONString(Object value, Appendable out) throws IOException {
		if (value == null) {
			out.append("null");
			return;
		}

		if (value instanceof String) {
			out.append('"');
			escape((String) value, out);
			out.append('"');
			return;
		}

		if (value instanceof Number) {
			if (value instanceof Double) {
				if (((Double) value).isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			} else if (value instanceof Float) {
				if (((Float) value).isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			} else {
				out.append(value.toString());
			}
			return;
		}

		if (value instanceof Boolean)
			out.append(value.toString());
		else if ((value instanceof JSONStreamAware))
			((JSONStreamAware) value).writeJSONString(out);
		else if ((value instanceof JSONAware))
			out.append(((JSONAware) value).toJSONString());
		else if (value instanceof Map<?, ?>)
			JSONObject.writeJSONString((Map<String, Object>) value, out);
		else if (value instanceof List<?>)
			JSONArray.writeJSONString((List<Object>) value, out);
		else {
			out.append('"');
			out.append(escape(value.toString()));
			out.append('"');
		}
	}

	/**
	 * Convert an object to JSON text.
	 * <p>
	 * If this object is a Map or a List, and it's also a JSONAware, JSONAware
	 * will be considered firstly.
	 * <p>
	 * 
	 * @see net.minidev.json.JSONObject#toJSONString(Map)
	 * @see net.minidev.json.JSONArray#toJSONString(List)
	 * 
	 * @return JSON text, or "null" if value is null or it's an NaN or an INF
	 *         number.
	 */
	public static String toJSONString(Object value) {
		StringBuilder sb = new StringBuilder();
		try {
			writeJSONString(value, sb);
		} catch (IOException e) {
			// can not append on a StringBuilder
		}
		return sb.toString();
	}

	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters
	 * (U+0000 through U+001F).
	 */
	public static String escape(String s) {
		if (s == null)
			return null;
		StringBuilder sb = new StringBuilder();
		JStylerObj.escape(s, sb);
		return sb.toString();
	}

	public static void escape(String s, Appendable ap) {
		if (s == null)
			return;
		JStylerObj.escape(s, ap);
	}
}
