package net.minidev.json.testMapping;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.minidev.json.JSONValue;

public class TestMapBeans extends TestCase {

	public void testSerObjInts() throws Exception {
		String s = "{\"vint\":[1,2,3]}";
		T1 r = new T1();
		r.vint = new int[] { 1, 2, 3 };
		String s2 = JSONValue.toJSONString(r);
		assertEquals(s, s2);
	}

	public void testSerObjIntKey() throws Exception {
		String s = "{\"data\":{\"1\":\"toto\"}}";
		T2 r = new T2();
		r.data = new HashMap<Integer, String>();
		r.data.put(1, "toto");
		String s2 = JSONValue.toJSONString(r);
		assertEquals(s, s2);
	}

	public void testSerObjEnumKey() throws Exception {
		String s = "{\"data\":{\"red\":10}}";
		T3 r = new T3();
		r.data = new HashMap<TestMapBeans.ColorEnum, Integer>();
		r.data.put(ColorEnum.red, 10);
		String s2 = JSONValue.toJSONString(r);
		assertEquals(s, s2);
	}

	public void testSerObjBool1() throws Exception {
		String s = "{\"data\":true}";
		T4 r = new T4();
		r.data = true;
		String s2 = JSONValue.toJSONString(r);
		assertEquals(s, s2);
	}
	
	public void testSerObjBool2() throws Exception {
		String s = "{\"data\":true}";
		T5 r = new T5();
		r.data = true;
		String s2 = JSONValue.toJSONString(r);
		assertEquals(s, s2);
	}

	public static class T1 {
		private int[] vint;

		public int[] getVint() {
			return vint;
		}

		public void setVint(int[] vint) {
			this.vint = vint;
		}
	}

	public static class T2 {
		private Map<Integer, String> data;

		public Map<Integer, String> getData() {
			return data;
		}

		public void setData(Map<Integer, String> data) {
			this.data = data;
		}
	}

	public static enum ColorEnum {
		bleu, green, red, yellow
	}

	public static class T3 {
		private Map<ColorEnum, Integer> data;

		public Map<ColorEnum, Integer> getData() {
			return data;
		}

		public void setData(Map<ColorEnum, Integer> data) {
			this.data = data;
		}
	}

	public static class T4 {
		private boolean data;

		public boolean getData() {
			return data;
		}

		public void setData(boolean data) {
			this.data = data;
		}
	}

	public static class T5 {
		private boolean data;

		public boolean isData() {
			return data;
		}

		public void setData(boolean data) {
			this.data = data;
		}
	}

}
