package net.minidev.json.serialiser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONAware;
import net.minidev.json.JSONAwareEx;
import net.minidev.json.JSONStreamAwareEx;
import net.minidev.json.JSONStyle;
import net.minidev.json.JSONUtil;
import net.minidev.json.JSONValue;

public class JsonWriter {
	private HashMap<Class<?>, JsonWriterI<?>> data;

	public JsonWriter() {
		data = new HashMap<Class<?>, JsonWriterI<?>>();
		init();
	}

	public JsonWriterI getWrite(Class cls) {
		return data.get(cls);
	}

	final static public JsonWriterI<JSONStreamAwareEx> JSONStreamAwareWriter = new JsonWriterI<JSONStreamAwareEx>() {
		public <E extends JSONStreamAwareEx> void writeJSONString(E value, Appendable out, JSONStyle compression)
				throws IOException {
			value.writeJSONString(out);
		}
	};

	final static public JsonWriterI<JSONStreamAwareEx> JSONStreamAwareExWriter = new JsonWriterI<JSONStreamAwareEx>() {
		public <E extends JSONStreamAwareEx> void writeJSONString(E value, Appendable out, JSONStyle compression)
				throws IOException {
			value.writeJSONString(out, compression);
		}
	};

	final static public JsonWriterI<JSONAwareEx> JSONJSONAwareExWriter = new JsonWriterI<JSONAwareEx>() {
		public <E extends JSONAwareEx> void writeJSONString(E value, Appendable out, JSONStyle compression)
				throws IOException {
			out.append(((JSONAwareEx) value).toJSONString(compression));
		}
	};

	final static public JsonWriterI<JSONAware> JSONJSONAwareWriter = new JsonWriterI<JSONAware>() {
		public <E extends JSONAware> void writeJSONString(E value, Appendable out, JSONStyle compression)
				throws IOException {
			out.append(((JSONAware) value).toJSONString());
		}
	};

	final static public JsonWriterI<Iterable<? extends Object>> JSONIterableWriter = new JsonWriterI<Iterable<? extends Object>>() {
		public <E extends Iterable<? extends Object>> void writeJSONString(E list, Appendable out, JSONStyle compression)
				throws IOException {
			boolean first = true;
			compression.arrayStart(out);
			for (Object value : list) {
				if (first) {
					first = false;
					compression.arrayfirstObject(out);
				} else {
					compression.arrayNextElm(out);
				}
				if (value == null)
					out.append("null");
				else
					JSONValue.writeJSONString(value, out, compression);
				compression.arrayObjectEnd(out);
			}
			compression.arrayStop(out);
		}
	};

	final static public JsonWriterI<Enum<?>> EnumWriter = new JsonWriterI<Enum<?>>() {
		public <E extends Enum<?>> void writeJSONString(E value, Appendable out, JSONStyle compression)
				throws IOException {
			@SuppressWarnings("rawtypes")
			String s = ((Enum) value).name();
			if (!compression.mustProtectValue(s))
				out.append(s);
			else {
				out.append('"');
				compression.escape(s, out);
				out.append('"');
			}
		}
	};

	final static public JsonWriterI<Map<String, ? extends Object>> JSONMapWriter = new JsonWriterI<Map<String, ? extends Object>>() {
		public <E extends Map<String, ? extends Object>> void writeJSONString(E map, Appendable out,
				JSONStyle compression) throws IOException {
			boolean first = true;
			compression.objectStart(out);
			/**
			 * do not use <String, Object> to handle non String key maps
			 */
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (first) {
					compression.objectFirstStart(out);
					first = false;
				} else {
					compression.objectNext(out);
				}
				writeJSONKV(entry.getKey().toString(), entry.getValue(), out, compression);
				compression.objectElmStop(out);
			}
			compression.objectStop(out);
		}
	};

	final static public JsonWriterI<Object> beansWriter = new JsonWriterI<Object>() {
		public <E> void writeJSONString(E value, Appendable out, JSONStyle compression) throws IOException {
			try {
				Class<?> nextClass = value.getClass();
				boolean needSep = false;
				out.append('{');
				while (nextClass != Object.class) {
					Field[] fields = nextClass.getDeclaredFields();
					for (Field field : fields) {
						int m = field.getModifiers();
						if ((m & (Modifier.STATIC | Modifier.TRANSIENT | Modifier.FINAL)) > 0)
							continue;
						Object v = null;
						if ((m & Modifier.PUBLIC) > 0) {
							v = field.get(value);
						} else {
							String g = JSONUtil.getGetterName(field.getName());
							Method mtd = null;

							try {
								mtd = nextClass.getDeclaredMethod(g);
							} catch (Exception e) {
							}
							if (mtd == null) {
								Class<?> c2 = field.getType();
								if (c2 == Boolean.TYPE || c2 == Boolean.class) {
									g = JSONUtil.getIsName(field.getName());
									mtd = nextClass.getDeclaredMethod(g);
								}
							}
							if (mtd == null)
								continue;
							v = mtd.invoke(value);
						}
						if (needSep)
							out.append(',');
						else
							needSep = true;
						writeJSONKV(field.getName(), v, out, compression);
					}
					nextClass = nextClass.getSuperclass();
				}
				out.append('}');
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};

	final static public JsonWriterI<Object> arrayWriter = new JsonWriterI<Object>() {
		public <E> void writeJSONString(E value, Appendable out, JSONStyle compression) throws IOException {

			Class<?> arrayClz = value.getClass();
			Class<?> c = arrayClz.getComponentType();

			out.append('[');
			boolean needSep = false;

			if (c.isPrimitive()) {
				if (c == int.class) {
					for (int b : ((int[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Integer.toString(b));
					}
				} else if (c == short.class) {
					for (short b : ((short[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Short.toString(b));
					}
				} else if (c == byte.class) {
					for (byte b : ((byte[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Byte.toString(b));
					}
				} else if (c == long.class) {
					for (long b : ((long[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Long.toString(b));
					}
				} else if (c == float.class) {
					for (float b : ((float[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Float.toString((float) b));
					}
				} else if (c == double.class) {
					for (double b : ((double[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						out.append(Double.toString((double) b));
					}
				} else if (c == boolean.class) {
					for (boolean b : ((boolean[]) value)) {
						if (needSep)
							out.append(',');
						else
							needSep = true;
						if (b)
							out.append("true");
						else
							out.append("false");
					}
				}
			} else {
				for (Object o : ((Object[]) value)) {
					if (needSep)
						out.append(',');
					else
						needSep = true;
					writeJSONString(o, out, compression);
				}
			}
			out.append(']');

		}
	};

	public void init() {
		register(new JsonWriterI<String>() {
			public void writeJSONString(String value, Appendable out, JSONStyle compression) throws IOException {
				if (!compression.mustProtectValue((String) value))
					out.append((String) value);
				else {
					out.append('"');
					JSONValue.escape((String) value, out, compression);
					out.append('"');
				}
			}
		}, String.class);

		register(new JsonWriterI<Double>() {
			public void writeJSONString(Double value, Appendable out, JSONStyle compression) throws IOException {
				if (value.isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			}
		}, Double.class);

		register(new JsonWriterI<Double>() {
			public void writeJSONString(Double value, Appendable out, JSONStyle compression) throws IOException {
				out.append('"');
				JSONValue.escape(value.toString(), out, compression);
				out.append('"');
			}
		}, Date.class);

		register(new JsonWriterI<Float>() {
			public void writeJSONString(Float value, Appendable out, JSONStyle compression) throws IOException {
				if (value.isInfinite())
					out.append("null");
				else
					out.append(value.toString());
			}
		}, Float.class);

		register(new JsonWriterI<Number>() {
			public void writeJSONString(Number value, Appendable out, JSONStyle compression) throws IOException {
				out.append(value.toString());
			}
		}, Integer.class, Long.class, Byte.class, Short.class, BigInteger.class);

		register(new JsonWriterI<Boolean>() {
			public void writeJSONString(Boolean value, Appendable out, JSONStyle compression) throws IOException {
				out.append(value.toString());
			}
		}, Boolean.class);

		register(new JsonWriterI<Boolean>() {
			public void writeJSONString(Boolean value, Appendable out, JSONStyle compression) throws IOException {
				out.append(value.toString());
			}
		}, Boolean.class);

	}

	public <T> void register(JsonWriterI<T> w, Class<?>... cls) {
		for (Class<?> c : cls)
			data.put(c, w);
	}

	/**
	 * Write a Key : value entry to a stream
	 */
	public static void writeJSONKV(String key, Object value, Appendable out, JSONStyle compression) throws IOException {
		if (key == null)
			out.append("null");
		else if (!compression.mustProtectKey(key))
			out.append(key);
		else {
			out.append('"');
			JSONValue.escape(key, out, compression);
			out.append('"');
		}
		compression.objectEndOfKey(out);
		if (value instanceof String) {
			if (!compression.mustProtectValue((String) value))
				out.append((String) value);
			else {
				out.append('"');
				JSONValue.escape((String) value, out, compression);
				out.append('"');
			}
		} else
			JSONValue.writeJSONString(value, out, compression);
	}
}
