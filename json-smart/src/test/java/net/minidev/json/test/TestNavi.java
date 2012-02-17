package net.minidev.json.test;

import java.util.Collection;

import junit.framework.TestCase;
import net.minidev.json.JSONNavi;
import net.minidev.json.JSONObject;

public class TestNavi extends TestCase {
	public void testNaviWrite() {
		JSONNavi<Collection<?>> nav = JSONNavi.newInstanceOrdered();
		nav.set("name", "jhone").set("age", 42).at("childName").add("fifi", "riri", "loulou").up().at("cat")
				.set("color", "red");
		String s1 = "{\"name\":\"jhone\",\"age\":42,\"childName\":[\"fifi\",\"riri\",\"loulou\"],\"cat\":{\"color\":\"red\"}}";
		assertEquals(s1, nav.toString());
	}

	public void testNaviWrite2() {
		// ContainerFactory.FACTORY_ORDERED JSONNavi should keep order
		JSONNavi<Collection<?>> nav = JSONNavi.newInstanceOrdered();
		nav.at("name").set("toto").up().set("tutu", "V2").at("size").set("width", 10).set("higth", 35).up(3)
				.set("FinUp", 1).at("array").add(0, 1, 2, 3, 4, 5);
		nav.at(-1);
		assertEquals("/array[5]", nav.getJPath());
		String s1 = "{'name':'toto','tutu':'V2','size':{'width':10,'higth':35},'FinUp':1,'array':[0,1,2,3,4,5]}"
				.replace('\'', '"');
		assertEquals(s1, nav.toString());
	}

	public void testNaviRead() {
		String json = "{name:foo,str:null,ar:[1,2,3,4]}";

		JSONNavi<JSONObject> nav = new JSONNavi<JSONObject>(json);
		nav.at(5);
		assertTrue("Navigator should be in error stat", nav.hasFailure());
		nav.root();
		assertEquals(3, nav.at("ar").at(2).asInt());
		nav.up(2);
		assertEquals(4, nav.at("ar").at(-1).asInt());
		nav.up(2);
		assertEquals("foo", nav.at("name").asString());
	}

	public void testNaviWriteArray() {
		String expected = "{'type':'bundle','data':[{'type':'object','name':'obj1'},{'type':'object','name':'obj2'}]}"
				.replace('\'', '"');
		JSONNavi<Collection<?>> nav = JSONNavi.newInstanceOrdered();
		nav.set("type", "bundle").at("data").array().at(0).set("type", "object").set("name", "obj1").up().at(1)
				.set("type", "object").set("name", "obj2").root();
		assertEquals(expected, nav.toString());

		nav = JSONNavi.newInstanceOrdered();
		nav.set("type", "bundle").at("data").array().atNext().set("type", "object").set("name", "obj1").up().atNext()
				.set("type", "object").set("name", "obj2").root();
		assertEquals(expected, nav.toString());
	}

}
