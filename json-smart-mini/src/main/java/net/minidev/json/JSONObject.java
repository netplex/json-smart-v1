package net.minidev.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports
 * java.util.Map interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author Uriel Chemouni <uchemouni@gmail.com>
 */
public class JSONObject extends HashMap<String, Object> implements JSONAware {
	private static final long serialVersionUID = -503443796854799292L;

	public JSONObject() {
		super();
	}

	/**
	 * Allow simply casting to Map<String, XXX>
	 */
	@SuppressWarnings("unchecked")
	public <T> T cast() {
		return (T) this;
	}

	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters
	 * (U+0000 through U+001F). It's the same as JSONValue.escape() only for
	 * compatibility here.
	 * 
	 * @see JSONValue#escape(String)
	 */
	public static String escape(String s) {
		return JSONValue.escape(s);
	}

	/**
	 * Convert a map to JSON text. The result is a JSON object. If this map is
	 * also a JSONAware, JSONAware specific behaviours will be omitted at this
	 * top level.
	 * 
	 * @see JSONValue#toJSONString(Object)
	 * 
	 * @return JSON text, or "null" if map is null.
	 */
	public static String toJSONString(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		try {
			writeJSONString(map, sb);
		} catch (IOException e) {
			// can not append on a StringBuilder
		}
		return sb.toString();
	}

	private static void toJSONString(String key, Object value, Appendable out) throws IOException {
		if (key == null)
			out.append("null");
		else {
			out.append('"');
			JSONValue.escape(key, out);
			out.append('"');
		}
		out.append(':');
		if (value instanceof String) {
			out.append('"');
			JSONValue.escape((String) value, out);
			out.append('"');
		} else
			JSONValue.writeJSONString(value, out);
	}

	public static String toString(String key, Object value) {
		StringBuilder sb = new StringBuilder();
		try {
			toJSONString(key, value, sb);
		} catch (IOException e) {
			// can not append on a StringBuilder
		}
		return sb.toString();
	}

	/**
	 * Allows creation of a JSONObject from a Map. After that, both the
	 * generated JSONObject and the Map can be modified independently.
	 */
	public JSONObject(Map<String, ?> map) {
		super(map);
	}

	/**
	 * Encode a map into JSON text and write it to out. If this map is also a
	 * JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific
	 * behaviours will be ignored at this top level.
	 * 
	 * @see JSONValue#writeJSONString(Object, Appendable)
	 */
	public static void writeJSONString(Map<String, Object> map, Appendable out) throws IOException {
		if (map == null) {
			out.append("null");
			return;
		}

		boolean first = true;

		out.append('{');
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (first)
				first = false;
			else
				out.append(',');
			toJSONString(entry.getKey(), entry.getValue(), out);
		}
		out.append('}');
	}

	/**
	 * serialize Object as json to an stream
	 */
	public void writeJSONString(Appendable out) throws IOException {
		writeJSONString(this, out);
	}

	public void merge(Object o2) {
		merge(this, o2);
	}

	protected static JSONObject merge(JSONObject o1, Object o2) {
		if (o2 == null)
			return o1;
		if (o2 instanceof JSONObject)
			return merge(o1, (JSONObject) o2);
		throw new RuntimeException("JSON megre can not merge a JSONObject with " + o2.getClass());
	}

	private static JSONObject merge(JSONObject o1, JSONObject o2) {
		if (o2 == null)
			return o1;
		for (String key : o1.keySet()) {
			Object value1 = o1.get(key);
			Object value2 = o2.get(key);
			if (value2 == null)
				continue;
			if (value1 instanceof JSONArray) {
				o1.put(key, merge((JSONArray) value1, value2));
				continue;
			}
			if (value1 instanceof JSONObject) {
				o1.put(key, merge((JSONObject) value1, value2));
				continue;
			}
			throw new RuntimeException("JSON megre can not merge a " + value1.getClass() + " with " + value2.getClass());
		}
		for (String key : o2.keySet()) {
			if (o1.containsKey(key))
				continue;
			o1.put(key, o2.get(key));
		}
		return o1;
	}

	protected static JSONArray merge(JSONArray o1, Object o2) {
		if (o2 == null)
			return o1;
		if (o1 instanceof JSONArray)
			return merge(o1, (JSONArray) o2);
		o1.add(o2);
		return o1;
	}

	private static JSONArray merge(JSONArray o1, JSONArray o2) {
		o1.addAll(o2);
		return o1;
	}

	public String toJSONString() {
		return toJSONString(this);
	}

	public String toString() {
		return toJSONString();
	}
}
