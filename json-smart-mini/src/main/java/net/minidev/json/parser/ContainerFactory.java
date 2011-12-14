package net.minidev.json.parser;

import java.util.List;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ContainerFactory;

/**
 * Container factory for creating containers for JSON object and JSON array.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class ContainerFactory {
	public final static ContainerFactory FACTORY = new ContainerFactory();
	
	/**
	 * @return A Map instance to store JSON object, or null if you want to use JSONObject.
	 */
	public Map<String,Object> createObjectContainer() {
		return new JSONObject();
	}
	
	/**
	 * @return A List instance to store JSON array, or null if you want to use JSONArray. 
	 */
	public List<Object> creatArrayContainer() {
		return new JSONArray();
	}
}
