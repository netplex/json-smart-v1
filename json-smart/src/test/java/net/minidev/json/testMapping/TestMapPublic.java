package net.minidev.json.testMapping;

import junit.framework.TestCase;
import net.minidev.json.JSONValue;

public class TestMapPublic extends TestCase {
	String MuliTyepJson = "{\"name\":\"B\",\"age\":120,\"cost\":12000,\"flag\":3,\"valid\":true,\"f\":1.2,\"d\":1.5,\"l\":12345678912345}";

	public void testSerObjMixte() throws Exception {
		T2 r = new T2();
		r.name = "B";
		r.age = 120;
		r.cost = 12000;
		r.flag = 3;
		r.valid = true;
		r.name = "B";
		r.f = 1.2F;
		r.d = 1.5;
		r.l = 12345678912345L;
		String s = JSONValue.toJSONString(r);

		assertEquals(MuliTyepJson, s);
	}

	public void testSerObjMixtePrim() throws Exception {
		T3 r = new T3();
		r.name = "B";
		r.age = 120;
		r.cost = 12000;
		r.flag = 3;
		r.valid = true;
		r.name = "B";
		r.f = 1.2F;
		r.d = 1.5;
		r.l = 12345678912345L;
		String s = JSONValue.toJSONString(r);
		assertEquals(MuliTyepJson, s);
	}

	public void testSerObjMixteAcc() throws Exception {
		T4 r = new T4();
		r.name = "B";
		r.age = 120;
		r.cost = 12000;
		r.flag = 3;
		r.valid = true;
		r.name = "B";
		r.f = 1.2F;
		r.d = 1.5;
		r.l = 12345678912345L;
		String s = JSONValue.toJSONString(r);
		assertEquals(MuliTyepJson, s);
	}

	public void testSerObjMixtePrimAcc() throws Exception {
		T5 r = new T5();
		r.name = "B";
		r.age = 120;
		r.cost = 12000;
		r.flag = 3;
		r.valid = true;
		r.name = "B";
		r.f = 1.2F;
		r.d = 1.5;
		r.l = 12345678912345L;
		String s = JSONValue.toJSONString(r);
		assertEquals(MuliTyepJson, s);
	}
	
	public static class T2 {
		public String name;
		public short age;
		public int cost;
		public byte flag;
		public boolean valid;
		public float f;
		public double d;
		public long l;
	}

	public static class T3 {
		public String name;
		public Short age;
		public Integer cost;
		public Byte flag;
		public Boolean valid;
		public Float f;
		public Double d;
		public Long l;
	}

	public static class T4 {
		private String name;
		private short age;
		private int cost;
		private byte flag;
		private boolean valid;
		private float f;
		private double d;
		private long l;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public short getAge() {
			return age;
		}

		public void setAge(short age) {
			this.age = age;
		}

		public int getCost() {
			return cost;
		}

		public void setCost(int cost) {
			this.cost = cost;
		}

		public byte getFlag() {
			return flag;
		}

		public void setFlag(byte flag) {
			this.flag = flag;
		}

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean valid) {
			this.valid = valid;
		}

		public float getF() {
			return f;
		}

		public void setF(float f) {
			this.f = f;
		}

		public double getD() {
			return d;
		}

		public void setD(double d) {
			this.d = d;
		}

		public long getL() {
			return l;
		}

		public void setL(long l) {
			this.l = l;
		}
	}

	public static class T5 {
		private String name;
		private Short age;
		private Integer cost;
		private Byte flag;
		private Boolean valid;
		private Float f;
		private Double d;
		private Long l;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Short getAge() {
			return age;
		}

		public void setAge(Short age) {
			this.age = age;
		}

		public Integer getCost() {
			return cost;
		}

		public void setCost(Integer cost) {
			this.cost = cost;
		}

		public Byte getFlag() {
			return flag;
		}

		public void setFlag(Byte flag) {
			this.flag = flag;
		}

		public Boolean getValid() {
			return valid;
		}

		public void setValid(Boolean valid) {
			this.valid = valid;
		}

		public Float getF() {
			return f;
		}

		public void setF(Float f) {
			this.f = f;
		}

		public Double getD() {
			return d;
		}

		public void setD(Double d) {
			this.d = d;
		}

		public Long getL() {
			return l;
		}

		public void setL(Long l) {
			this.l = l;
		}
	}

}
